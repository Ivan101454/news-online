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
        return News.builder().newsId(UUID.fromString("f89034aa-5f38-497c-b56b-e292936e2f1e")).headerNews("Почему все хотят попасть на Щелкунчика")
                .isPublished(true).category(Category.builder().categoryId(UUID.fromString("25f4f0cc-95ca-4622-9640-3de7a82cded9")).section(Section.PEOPLE).build())
                .shortDescription("«„Щелкунчик“ — это гениальное произведение». Закулисье самого ожидаемого балета года").contentLink("static/text/news1.txt").build();
    }

}
