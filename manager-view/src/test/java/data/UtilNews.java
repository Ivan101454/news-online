package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.enums.Section;

import java.util.UUID;

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

    public CategoryDto createCategoryDto() {
        return new CategoryDto(UUID.randomUUID(), Section.PEOPLE, null);
    }

    @SneakyThrows
    public String writeNewsAsJsonString() {
        NewsDto news = createNews();
        return new  ObjectMapper().writeValueAsString(news);
    }

    @SneakyThrows
    public String writeCategorieAsJsonString() {
        CategoryDto categoryDto = createCategoryDto();
        return new  ObjectMapper().writeValueAsString(categoryDto);
    }

    public String writeListOfJsonNews() {
        String s = writeNewsAsJsonString();
        return """
                [
                %s, %s
                ]
                """.formatted(s, s);
    }

    public MockMultipartFile getFile() {
        return new MockMultipartFile("file", "Sample file content".getBytes());
    }
}
