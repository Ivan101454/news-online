package ru.clevertec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import ru.clevertec.newsonline.controller.NewsController;
import ru.clevertec.newsonline.dto.AuthorDto;
import ru.clevertec.newsonline.dto.CommentDto;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.filter.CommentFilter;
import ru.clevertec.newsonline.service.CommentService;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        NewsController controll = context.getBean(NewsController.class);
//        NewsDto newsDto = new NewsDto("header", null, null, true, null,
//                "description", "body", null, null);

//        CommentDto commentDto = new CommentDto(null, "kot dog person", null, null);
        CommentFilter kot = new CommentFilter("dog");
//        controll.createComment(commentDto, UUID.fromString("03022119-2f56-4144-864b-15538e982af3"));

        controll.findCommentByWord(kot, 1, 10).getBody().forEach(System.out::println);
    }
}
