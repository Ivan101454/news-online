package ru.clevertec.newsonline.controller.newcontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.filter.NewsFilter;
import ru.clevertec.newsonline.newService.service.interfaces.CategoryServicePort;
import ru.clevertec.newsonline.newService.service.interfaces.NewsServicePort;
import ru.clevertec.newsonline.newService.service.interfaces.PictureServicePort;
import ru.clevertec.newsonline.util.SaveImage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("catalogue-api/news")
public class NewssController {

    private final NewsServicePort newsServicePort;
    private final CategoryServicePort categoryServicePort;
    private final PictureServicePort pictureServicePort;

    @GetMapping("list")
    public List<NewsDto> findNews() {
        return newsServicePort.findAll();
    }

    @GetMapping("list-pagination")
    public List<NewsDto> findNewsWithPagination(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return newsServicePort.findByPage(pageNumber, pageSize);
    }

    @GetMapping("list-by-filter")
    public List<NewsDto> findNewsByFilter(
            @RequestParam(name = "headerNews", required = false) String headerNews,
            @RequestParam(name = "shortDescription", required = false) String shortDescription,
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return newsServicePort.findEntityByFilter(new NewsFilter(headerNews, shortDescription), pageNumber, pageSize);
    }

    @PostMapping()
    public ResponseEntity<?> createNews( @RequestPart("newsDto") @Valid NewsDto newsDto,
                                         @RequestPart("categoryDto") @Valid CategoryDto categoryDto,
                                         @RequestPart(value = "image", required = false) MultipartFile image,
                                        BindingResult bindingResult,
                                        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Optional<NewsDto> news = newsServicePort.create(newsDto);
            if (image != null && !image.isEmpty() && news.isPresent()) {
                String persist = SaveImage.persist(image);
                PictureDto pictureDto = new PictureDto(image.getName(), persist, null);
                Optional<PictureDto> pictureDtoSave = pictureServicePort.create(pictureDto);
                pictureDtoSave.ifPresent(x -> newsServicePort.addPicture(newsDto.articleId(), x));
            }
            news.ifPresent(dto -> categoryServicePort.addNews(categoryDto.section(), dto));
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/catalogue-api/news/list")
                            .build(Map.of("article", newsDto.articleId())))
                    .body(news);
        }
    }
}
