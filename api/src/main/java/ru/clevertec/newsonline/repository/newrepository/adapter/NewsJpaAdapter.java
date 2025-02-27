package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.AuthorDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;
import ru.clevertec.newsonline.newService.service.interfaces.NewsPersistencePort;
import ru.clevertec.newsonline.repository.IFilterEntityRepository;
import ru.clevertec.newsonline.repository.newrepository.AuthorRepository;
import ru.clevertec.newsonline.repository.newrepository.NewsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NewsJpaAdapter implements NewsPersistencePort {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final IFilterEntityRepository<News, NewsFilter> iFilterEntityRepository;
    private final NewsMapper newsMapper;

    @Override
    public List<NewsDto> findAll() {
        return newsRepository.findAll().stream().map(newsMapper::newsToNewsDto).toList();
    }

    @Override
    public Page<NewsDto> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable).map(newsMapper::newsToNewsDto);
    }

    @Override
    public Optional<NewsDto> findById(UUID id) {
        return newsRepository.findById(id).map(newsMapper::newsToNewsDto);
    }

    @Override
    public Optional<NewsDto> findByArticleId(int id) {
        return newsRepository.findByArticleId(id).map(newsMapper::newsToNewsDto);
    }

    @Override
    public NewsDto save(NewsDto newsDto) {
        AuthorDto authorDto = newsDto.author();
        News news = newsMapper.newsDtoToNews(newsDto);
        News newsSave = newsRepository.saveAndFlush(newsMapper.newsDtoToNews(newsDto));
        Optional<Author> author = authorRepository.findByNameAuthorIgnoreCaseAndLastNameIgnoreCase(authorDto.nameAuthor(), authorDto.lastName());
        if (author.isPresent()) {
            author.ifPresent(x -> x.addNews(newsSave));
        } else {
            Author authorMap = newsMapper.authorDtoToAuthor(authorDto);
            authorMap.setWriteNews(new ArrayList<>());
            Author save = authorRepository.saveAndFlush(authorMap);
            save.addNews(newsSave);
        }
        return newsDto;
    }

    @Override
    public void deleteByArticleId(int id) {
        newsRepository.findByArticleId(id)
                .ifPresentOrElse(newsRepository::delete, () -> {throw new NoSuchElementException("Нет такого пользователя");});
    }

    @Override
    public List<NewsDto> filterWord(NewsFilter newsFilter, Pageable pageable) {
        return iFilterEntityRepository.filterWord(newsFilter, News.class, pageable).stream()
                .map(newsMapper::newsToNewsDto).toList();
    }
}
