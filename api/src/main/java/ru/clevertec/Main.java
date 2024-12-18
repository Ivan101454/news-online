package ru.clevertec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.clevertec.newsonline.controller.NewsController;
import ru.clevertec.newsonline.dto.AuthorDto;
import ru.clevertec.newsonline.dto.NewsDto;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        NewsController controll = context.getBean(NewsController.class);
        NewsDto newsDto = new NewsDto("header", null, null, true, null,
                "description", "body", null, null);

        controll.create(newsDto);
    }
}
