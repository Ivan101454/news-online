package Data;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.enums.Section;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class CreateData {

    public NewsDto createNewsDto() {
        return new NewsDto("Под Минском госзастройщик возводит стильные коттеджи. Для нас с вами",
                LocalDateTime.now(), 1234567, true, "Прямо сейчас под Минском реализуется новый проект, в некотором смысле экспериментальный. В поселке Озерище растут современные (и весьма симпатичные) дома, которые в дальнейшем планируют выставить на аукцион. Смотрим на диковинку.",
                null, null);
    }

    public CategoryDto createCategoryDto() {
        return new CategoryDto(UUID.randomUUID(), Section.PEOPLE, null);
    }

    public PictureDto createPictureDto() {
        return new PictureDto(UUID.randomUUID(), "Picture", "/image/img.jpg");
    }

    public CommentDto createCommentDto() {
        return new CommentDto(LocalDateTime.now(), "First comment");
    }

    public List<NewsDto> createListNewsDto() {
        ArrayList<NewsDto> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(new NewsDto("Под Минском госзастройщик возводит стильные коттеджи. Для нас с вами",
                    LocalDateTime.now(), 1234567+i, true, "Прямо сейчас под Минском реализуется новый проект, в некотором смысле экспериментальный. В поселке Озерище растут современные (и весьма симпатичные) дома, которые в дальнейшем планируют выставить на аукцион. Смотрим на диковинку.",
                    null, null));
        }
        return list;
    }

    @SneakyThrows
    public String writeNewsAsJsonString() {
        NewsDto news = createNewsDto();
        return new ObjectMapper().writeValueAsString(news);
    }

    @SneakyThrows
    public String writeCategorieAsJsonString() {
        CategoryDto categoryDto = createCategoryDto();
        return new  ObjectMapper().writeValueAsString(categoryDto);
    }


}
