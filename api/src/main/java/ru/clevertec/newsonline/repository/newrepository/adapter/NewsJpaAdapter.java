package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.service.interfaces.NewsPersistencePort;
import ru.clevertec.newsonline.repository.newrepository.NewsRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NewsJpaAdapter implements NewsPersistencePort {

    private final NewsRepository newsRepository;
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
    public NewsDto save(NewsDto newsDto) {
        newsRepository.save(newsMapper.newsDtoToNews(newsDto));
        return newsDto;
    }

    @Override
    public void delete(UUID id) {
        newsRepository.findById(id)
                .ifPresentOrElse(newsRepository::delete, () -> {throw new NoSuchElementException("Нет такого пользователя");});
    }
}
