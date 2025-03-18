package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.Picture;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.mapper.JpaContextAuthor;
import ru.clevertec.newsonline.mapper.JpaContextNews;
import ru.clevertec.newsonline.mapper.JpaContextNewsCategory;
import ru.clevertec.newsonline.mapper.JpaContextPictureNews;
import ru.clevertec.newsonline.mapper.JpaContextUser;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.AuthorDto;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.enums.Section;
import ru.clevertec.newsonline.newService.filter.NewsFilter;
import ru.clevertec.newsonline.newService.service.interfaces.NewsPersistencePort;
import ru.clevertec.newsonline.repository.IFilterEntityRepository;
import ru.clevertec.newsonline.repository.newrepository.AuthorRepository;
import ru.clevertec.newsonline.repository.newrepository.CategoryRepository;
import ru.clevertec.newsonline.repository.newrepository.NewsRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NewsJpaAdapter implements NewsPersistencePort {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;
    private final IFilterEntityRepository<News, NewsFilter> iFilterEntityRepository;
    private final NewsMapper newsMapper;
    private final JpaContextNews jpaCtx;
    private final JpaContextAuthor jpaCtxA;
    private final JpaContextUser jpaCtxU;
    private final JpaContextNewsCategory jpaCtxNC;
    private final JpaContextPictureNews jpaCtxPN;

    @Override
    public List<NewsDto> findAll() {
        return newsRepository.findAll().stream().map(newsMapper::newsToNewsDto).toList();
    }

    @Override
    public Page<NewsDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable).map(newsMapper::newsToNewsDto);
    }

    @Cacheable(value = "NEWS_CACHE", key = "#p0")
    @Override
    public Optional<NewsDto> findByArticleId(int articleId) {
        return newsRepository.findByArticleId(articleId).map(newsMapper::newsToNewsDto);
    }

    @CachePut(value = "NEWS_CACHE", key = "#result.articleId()")
    @Override
    public NewsDto save(NewsDto newsDto) {
        newsRepository.saveAndFlush(newsMapper.newsDtoToNews(newsDto, jpaCtx, jpaCtxA, jpaCtxNC));
        return newsDto;
    }

    @CacheEvict(value = "NEWS_CACHE", key = "#p0")
    @Override
    public void deleteByArticleId(int articleId) {
        newsRepository.findByArticleId(articleId)
                .ifPresentOrElse(newsRepository::delete, () -> {throw new NoSuchElementException("Нет новости с таким артиклем");});
    }

    @CachePut(value = "NEWS_CACHE", key = "#p0")
    @Override
    public Optional<NewsDto> update(int articleId, NewsDto newsDto, CategoryDto categoryDto) {
        Optional<News> byArticleId = newsRepository.findByArticleId(articleId);
        Optional<Category> bySection = categoryRepository.findBySection(categoryDto.section());
        byArticleId.ifPresentOrElse(x -> {
                    x.setHeaderNews(newsDto.headerNews());
                    x.setPublished(newsDto.isPublished() != null ? newsDto.isPublished() : false);
                    x.setShortDescription(newsDto.shortDescription());
                    bySection.ifPresent(x::setCategory);
                    }
                , () -> {
                    throw new NotFoundException("Сущность не найдена по id");
                });
        return Optional.of(newsMapper.newsToNewsDto(byArticleId.orElseThrow()));
    }

    @CachePut(value = "NEWS_CACHE", key = "#p0")
    @Override
    public List<NewsDto> filterWord(NewsFilter newsFilter, Pageable pageable) {
        return iFilterEntityRepository.filterWord(newsFilter, News.class, pageable).stream()
                .map(newsMapper::newsToNewsDto).toList();
    }

    @CachePut(value = "NEWS_CACHE", key = "#p0")
    @Override
    public Optional<NewsDto> addCommentToNewsList(int articleId, CommentDto commentDto) {
        Optional<News> byArticleId = newsRepository.findByArticleId(articleId);
        Comment comment = newsMapper.commentDtoToComment(commentDto, jpaCtx, jpaCtxU);
        byArticleId.ifPresent(news -> news.addComment(comment));
        return byArticleId.map(newsMapper::newsToNewsDto);
    }

    @Override
    public Optional<NewsDto> addPictureToNewsList(int articleId, PictureDto pictureDto) {
        Optional<News> byArticleId = newsRepository.findByArticleId(articleId);
        Picture picture = newsMapper.pictureDtoToPicture(pictureDto, jpaCtxPN);
        byArticleId.ifPresent(news -> news.addPicture(picture));
        return byArticleId.map(newsMapper::newsToNewsDto);
    }
}
