package ru.clevertec.newsonline.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import ru.clevertec.newsonline.data.NewsTestBuilder;
import ru.clevertec.newsonline.entity.News;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
//@Sql(scripts = "classpath:db/changelog/db.changelog-master-test.yaml")
@RequiredArgsConstructor
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NewsRepositoryTest {

//    private final NewsRepository newsRepository;

    @Test
    public void findNewsById() {

        //given
        UUID newsId = NewsTestBuilder.builder().build().buildNews().getNewsId();
        News expectedNews = NewsTestBuilder.builder().build().buildNews();

        //when
//        News actualNews = ((CrudRepository<News, UUID>) newsRepository).findById(newsId).orElseThrow();

        //then
//        assertEquals(expectedNews.getHeaderNews(), actualNews.getHeaderNews());

    }

}