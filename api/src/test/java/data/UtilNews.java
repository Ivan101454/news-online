package data;

import lombok.experimental.UtilityClass;
import ru.clevertec.newsonline.newService.dto.NewsDto;

@UtilityClass
public class UtilNews {

    public NewsDto createNews() {
        return new NewsDto("Это боль, больше никогда! Провел неделю с «андроидом» после 12 лет на «айфоне»",
                null, 5354289, false, "Что будет, если пользователь «айфонов» с 12-летним стажем перейдет на «андроид»? Спойлер — ничего хорошего. Таким подопытным стал автор этого материала. Я на время сменил свой уже несвежий iPhone 12 на актуальный Google Pixel 9 и получил лишь многократное повышение температуры в области чуть пониже спины. ",
                "file1", null, null, null);
    }

    public NewsDto createNotValidNews() {
        return new NewsDto(" ",
                null, 99, false, "",
                "file1", null, null, null);
    }
}
