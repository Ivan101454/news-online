package ru.clevertec.newsonline.data;


import lombok.Builder;
import lombok.Data;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.Picture;
import ru.clevertec.newsonline.enums.Section;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class NewsTestBuilder {

    public News buildNews() {
        return News.builder().newsId(UUID.fromString("12345678-1234-5678-1234-567812345678")).headerNews("Breaking news").dateOfNews(LocalDateTime.now())
                .isPublished(true).category(Category.builder().categoryId(UUID.randomUUID()).section(Section.PEOPLE).build())
                .shortDescription("This is news").contentLink("static/text/news1.txt").build();
    }

}
