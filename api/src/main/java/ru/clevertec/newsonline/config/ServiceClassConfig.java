package ru.clevertec.newsonline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.filter.CommentFilter;
import ru.clevertec.newsonline.filter.NewsFilter;
import ru.clevertec.newsonline.repository.CommentRepository;
import ru.clevertec.newsonline.repository.NewsRepository;
import ru.clevertec.newsonline.service.CommentService;
import ru.clevertec.newsonline.service.NewsService;

@Configuration
public class ServiceClassConfig {


    @Bean
    public NewsService createNewsService(NewsRepository newsRepository) {

        return new NewsService<News, NewsFilter>(newsRepository, newsRepository);
    }
    @Bean
    public CommentService createCommentService(CommentRepository commentRepository) {

        return new CommentService<Comment, CommentFilter>(commentRepository, commentRepository);
    }


}
