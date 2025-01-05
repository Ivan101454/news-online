package ru.clevertec.newsonline.data;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.filter.CommentFilter;

import java.util.UUID;

@Builder
@Data
public class CommentTestBuilder {

    public Comment createComment() {
        return Comment.builder().commentId(UUID.fromString("62fb1340-d3ab-4735-bcf3-fe4de8bd1093"))
                .news(News.builder().newsId(UUID.fromString("a03bd6ce-123d-4b73-8d24-6324971d0a67")).build())
                .textComment("Hello! It\"s great to see you here! How\"s your day going? \uD83D\uDE0A").build();
    }

    public Comment createCommentFifth() {
        return Comment.builder().commentId(UUID.fromString("0054eb75-ca17-4115-842c-46f7a25ff448"))
                .news(News.builder().newsId(UUID.fromString("a03bd6ce-123d-4b73-8d24-6324971d0a67")).build())
                .textComment("Повторите попытку позже.").build();
    }

    public Comment createNewComment() {
        return Comment.builder().commentId(UUID.fromString("2fd4ce9c-4f2d-4460-a29e-0046de388599"))
                .news(News.builder().newsId(UUID.fromString("a03bd6ce-123d-4b73-8d24-6324971d0a67")).build())
                .textComment("Новый комметарий").build();
    }

    public CommentFilter createCommentFilter() {
        return new CommentFilter("great");
    }

}
