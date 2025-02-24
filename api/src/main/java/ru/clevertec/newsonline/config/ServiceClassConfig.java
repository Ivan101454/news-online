package ru.clevertec.newsonline.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.clevertec.newsonline.newService.service.NewsService;
import ru.clevertec.newsonline.repository.newrepository.adapter.NewsJpaAdapter;


@Configuration
public class ServiceClassConfig {


    @Bean
    public NewsService createNewsService(NewsJpaAdapter newsJpaAdapter) {
        return new NewsService(newsJpaAdapter);
    }

    @Bean
    public ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }


}
