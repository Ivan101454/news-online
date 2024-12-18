package ru.clevertec.newsonline.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.repository.NewsRepository;
import ru.clevertec.newsonline.service.NewsService;

@Configuration
@RequiredArgsConstructor
public class RepoConfig {

    private final NewsRepository repository;

    @Bean
    public NewsService createNewsService() {

        return new NewsService<News>(repository);
    }
}
