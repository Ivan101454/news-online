package ru.clevertec.newsonline.controller.newcontroller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;
import ru.clevertec.newsonline.newService.service.interfaces.CategoryServicePort;
import ru.clevertec.newsonline.newService.service.interfaces.NewsServicePort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class NewssControllerTest {

    @Mock
    private NewsServicePort newsServicePort;
    @Mock
    private CategoryServicePort categoryServicePort;
    @InjectMocks
    private NewssController newssController;

    @Test
    void findNews_ShouldReturnListAllNews() {
        //given
        ArrayList<NewsDto> list = new ArrayList<>();
                list.add(new NewsDto("Это боль, больше никогда! Провел неделю с «андроидом» после 12 лет на «айфоне»",
                        null, 5354289, false, "Что будет, если пользователь «айфонов» с 12-летним стажем перейдет на «андроид»? Спойлер — ничего хорошего. Таким подопытным стал автор этого материала. Я на время сменил свой уже несвежий iPhone 12 на актуальный Google Pixel 9 и получил лишь многократное повышение температуры в области чуть пониже спины. ",
                        null, null));

        doReturn(list)
                .when(newsServicePort).findAll();
        //when
        List<NewsDto> newsResultList = newssController.findNews();

        //then
        assertEquals(list.getFirst(), newsResultList.getFirst());
    }

    @Test
    void findNewsWithPagination_ShouldReturnListWithSkipAndLimitConstraint() {
        //given
        List<NewsDto> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new NewsDto("Это боль, больше никогда! Провел неделю с «андроидом» после 12 лет на «айфоне»",
                    null, 5354289+i, false, "Что будет, если пользователь «айфонов» с 12-летним стажем перейдет на «андроид»? Спойлер — ничего хорошего. Таким подопытным стал автор этого материала. Я на время сменил свой уже несвежий iPhone 12 на актуальный Google Pixel 9 и получил лишь многократное повышение температуры в области чуть пониже спины. ",
                    null, null));
        }
        List<NewsDto> expect = list.stream().skip(10).limit(10).toList();

        doReturn(list.stream().skip(10).limit(10).toList())
                .when(newsServicePort).findByPage(2, 10);
        //when
        List<NewsDto> newsWithPagination = newssController.findNewsWithPagination(2, 10);

        //then
        assertEquals(expect.getFirst(), newsWithPagination.getFirst());
        assertEquals(expect.getLast(), newsWithPagination.getLast());
        assertEquals(expect.size(), newsWithPagination.size());

    }

    @Test
    void findNewsByFilter_ShouldReturnNewsWithTitleMatchesWithFilter() {
        //given
        List<NewsDto> list = new ArrayList<>();
            list.add(new NewsDto("Это боль, больше никогда! Провел неделю с «андроидом» после 12 лет на «айфоне»",
                    null, 5354289, false, "Что будет, если пользователь «айфонов» с 12-летним стажем перейдет на «андроид»? Спойлер — ничего хорошего. Таким подопытным стал автор этого материала. Я на время сменил свой уже несвежий iPhone 12 на актуальный Google Pixel 9 и получил лишь многократное повышение температуры в области чуть пониже спины. ",
                    null, null));
            list.add(new NewsDto("Под Минском госзастройщик возводит стильные коттеджи. Для нас с вами",
                    null, 5354290, false, "Что будет, если пользователь «айфонов» с 12-летним стажем перейдет на «андроид»? Спойлер — ничего хорошего. Таким подопытным стал автор этого материала. Я на время сменил свой уже несвежий iPhone 12 на актуальный Google Pixel 9 и получил лишь многократное повышение температуры в области чуть пониже спины. ",
                    null, null));

        doReturn(List.of(new NewsDto("Под Минском госзастройщик возводит стильные коттеджи. Для нас с вами",
                null, 5354290, false, "Что будет, если пользователь «айфонов» с 12-летним стажем перейдет на «андроид»? Спойлер — ничего хорошего. Таким подопытным стал автор этого материала. Я на время сменил свой уже несвежий iPhone 12 на актуальный Google Pixel 9 и получил лишь многократное повышение температуры в области чуть пониже спины. ",
                null, null)))
                .when(newsServicePort).findEntityByFilter(new NewsFilter("коттетжи", null), 1, 10);

        //when
        List<NewsDto> filter = newsServicePort.findEntityByFilter(new NewsFilter("коттетжи", null), 1, 10);

        //then
        assertEquals(list.get(1), filter.getFirst());
    }

    @Test
    void createPart() {
    }
}
