package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.newsonline.newService.dto.NewsDto;

@UtilityClass
public class UtilNews {

    public NewsDto createNews() {
        return new NewsDto("Это боль, больше никогда! Провел неделю с «андроидом» после 12 лет на «айфоне»",
                null, 5354289, false, "Что будет, если пользователь «айфонов» с 12-летним стажем перейдет на «андроид»? Спойлер — ничего хорошего. Таким подопытным стал автор этого материала. Я на время сменил свой уже несвежий iPhone 12 на актуальный Google Pixel 9 и получил лишь многократное повышение температуры в области чуть пониже спины. ",
                null, null);
    }

    public NewsDto createNotValidNews() {
        return new NewsDto(" ",
                null, 99, false, "",
                null, null);
    }

    @SneakyThrows
    public String writeNewsAsJsonString() {
        NewsDto news = createNews();
        return new  ObjectMapper().writeValueAsString(news);
    }

    @SneakyThrows
    public String writeCategorieAsJsonString() {
        NewsDto news = createCa();
        return new  ObjectMapper().writeValueAsString(news);
    }

    public MockMultipartFile getFile() {
        return new MockMultipartFile("file", "Sample file content".getBytes());
    }
}
