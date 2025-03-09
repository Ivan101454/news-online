package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.mapper.JpaContextAuthor;
import ru.clevertec.newsonline.mapper.JpaContextNews;
import ru.clevertec.newsonline.mapper.JpaContextUser;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.AuthorDto;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
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
    private final IFilterEntityRepository<News, NewsFilter> iFilterEntityRepository;
    private final NewsMapper newsMapper;
    private final JpaContextNews jpaCtx;
    private final JpaContextAuthor jpaCtxA;
    private final JpaContextUser jpaCtxU;

    @Override
    public List<NewsDto> findAll() {
        return newsRepository.findAll().stream().map(newsMapper::newsToNewsDto).toList();
    }

    @Override
    public Page<NewsDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable).map(newsMapper::newsToNewsDto);
    }

    @Override
    public Optional<NewsDto> findByArticleId(int id) {
        return newsRepository.findByArticleId(id).map(newsMapper::newsToNewsDto);
    }

    @Override
    public NewsDto save(NewsDto newsDto) {
        newsRepository.saveAndFlush(newsMapper.newsDtoToNews(newsDto, jpaCtx, jpaCtxA));
        return newsDto;
    }

    @Override
    public void deleteByArticleId(int id) {
        newsRepository.findByArticleId(id)
                .ifPresentOrElse(newsRepository::delete, () -> {throw new NoSuchElementException("Нет новости с таким артиклем");});
    }

    @Override
    public void update(int id, NewsDto newsDto) {
        Optional<News> byArticleId = newsRepository.findByArticleId(id);
        byArticleId.ifPresentOrElse(x -> {
                    x.setHeaderNews(newsDto.headerNews());
                    x.setPublished(newsDto.isPublished() != null ? newsDto.isPublished() : false);
                    x.setShortDescription(newsDto.shortDescription());
                    x.setContentLink(newsDto.contentLink());}
                , () -> {
                    throw new NotFoundException("Сущность не найдена по id");
                });
    }

    @Override
    public List<NewsDto> filterWord(NewsFilter newsFilter, Pageable pageable) {
        return iFilterEntityRepository.filterWord(newsFilter, News.class, pageable).stream()
                .map(newsMapper::newsToNewsDto).toList();
    }

    @Override
    public void addCommentToNewsList(int id, CommentDto commentDto) {
        Optional<News> byArticleId = newsRepository.findByArticleId(id);
        Comment comment = newsMapper.commentDtoToComment(commentDto, jpaCtx, jpaCtxU);
        byArticleId.ifPresent(news -> news.addComment(comment));
    }
}
