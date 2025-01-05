package ru.clevertec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
//        NewsController controll = context.getBean(NewsController.class);
//        NewsDto newsDto = new NewsDto("header", null, null, true, null,
//                "description", "body", null, null);

//        CommentDto commentDto = new CommentDto(null, "kot dog person", null, null);
//        CommentFilter kot = new CommentFilter("dog");
//        controll.createComment(commentDto, UUID.fromString("03022119-2f56-4144-864b-15538e982af3"));

//        controll.findCommentByWord(kot, 1, 10).getBody().forEach(System.out::println);
    }
}
