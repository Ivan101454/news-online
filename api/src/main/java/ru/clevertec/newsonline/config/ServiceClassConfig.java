package ru.clevertec.newsonline.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.clevertec.newsonline.newService.service.CategoryService;
import ru.clevertec.newsonline.newService.service.NewsService;
import ru.clevertec.newsonline.newService.service.PictureService;
import ru.clevertec.newsonline.newService.service.UserService;
import ru.clevertec.newsonline.repository.newrepository.adapter.CategoryJpaAdapter;
import ru.clevertec.newsonline.repository.newrepository.adapter.NewsJpaAdapter;
import ru.clevertec.newsonline.repository.newrepository.adapter.PictureJpaAdapter;
import ru.clevertec.newsonline.repository.newrepository.adapter.UserJpaAdapter;


@Configuration
public class ServiceClassConfig {

    @Bean
    public NewsService newsService(NewsJpaAdapter newsJpaAdapter) {
        return new NewsService(newsJpaAdapter);
    }

    @Bean
    public UserService userService(UserJpaAdapter userJpaAdapter) {
        return new UserService(userJpaAdapter);
    }

    @Bean
    public CategoryService categoryService(CategoryJpaAdapter categoryJpaAdapter) {
        return new CategoryService(categoryJpaAdapter);
    }

    @Bean
    public PictureService pictureService(PictureJpaAdapter pictureJpaAdapter) {
        return new PictureService(pictureJpaAdapter);
    }


//    @Bean
//    public ObjectMapper createObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new Jdk8Module());
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        return objectMapper;
//    }
}
