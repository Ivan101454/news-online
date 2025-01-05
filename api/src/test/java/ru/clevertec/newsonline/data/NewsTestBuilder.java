package ru.clevertec.newsonline.data;


import lombok.Builder;
import lombok.Data;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.Picture;
import ru.clevertec.newsonline.enums.Section;
import ru.clevertec.newsonline.filter.NewsFilter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class NewsTestBuilder {

    public News buildNews() {
        return News.builder().newsId(UUID.fromString("a03bd6ce-123d-4b73-8d24-6324971d0a67")).headerNews("Почему все хотят попасть на Щелкунчика")
                .isPublished(true).category(Category.builder().categoryId(UUID.fromString("25f4f0cc-95ca-4622-9640-3de7a82cded9")).section(Section.PEOPLE).build())
                .shortDescription("«„Щелкунчик“ — это гениальное произведение». Закулисье самого ожидаемого балета года").contentLink("static/text/news1.txt").build();
    }

    public News buildFifthNews() {
        return News.builder().newsId(UUID.fromString("7753980b-dda9-428c-b1f0-58bc4d24e138")).headerNews("Постараемся обойтись без «китайцев». Ищем подержанные электромобили")
                .isPublished(true).category(Category.builder().categoryId(UUID.fromString("3de78717-14f5-491e-a375-e122ec4dcbe2")).section(Section.CAR).build())
                .shortDescription("Хит сезона — новые или слегка подержанные «электрички» из Китая. Но на этот же бюджет можно поискать и более возрастные модели западных брендов. Посмотрели на «Автобарахолке» электромобили не старше 2019 года выпуска.").contentLink("static/text/news5.txt").build();
    }

    public News buildNewsForSave() {
        return News.builder().newsId(UUID.fromString("7289c37a-8bfa-4092-872b-f1e4a8c319c6")).headerNews("Новая новость о машине")
                .isPublished(true).category(Category.builder().categoryId(UUID.fromString("3de78717-14f5-491e-a375-e122ec4dcbe2")).section(Section.CAR).build())
                .shortDescription("Что же придумал Илон Маск").contentLink("static/text/news100.txt").build();
    }

    public NewsFilter buildNewsFilter() {
        return new NewsFilter("щелкунчик", "гениал");
    }

}
