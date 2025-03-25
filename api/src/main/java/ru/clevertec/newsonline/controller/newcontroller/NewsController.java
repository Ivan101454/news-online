package ru.clevertec.newsonline.controller.newcontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.service.interfaces.NewsServicePort;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("catalogue-api/news/{newsArticle:\\d+}")
public class NewsController {

    private final NewsServicePort newsServicePort;
    private final MessageSource messageSource;

    @ModelAttribute("news")
    public NewsDto getNews(@PathVariable("newsArticle") int newsArticle) {
        return newsServicePort.findByArticleId(newsArticle).orElseThrow(
                () -> new NoSuchElementException("{catalogue.errors.news.not_found}")
        );
    }

    @GetMapping()
    public NewsDto findNews(@ModelAttribute("news") NewsDto newsDto) {
        return newsDto;
    }

    @PatchMapping()
    public ResponseEntity<Void> updateNews(@RequestPart("newsDto") @Valid NewsDto update,
                                           @RequestPart("categoryDto") @Valid CategoryDto categoryDto,
                                           @RequestPart(value = "image", required = false) MultipartFile image,
                                            BindingResult bindingResult, Locale locale) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            newsServicePort.update(update.articleId(), update, categoryDto);
            if (image != null && !image.isEmpty()) {
                newsServicePort.addPicture(update.articleId(), image);
            }
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteNews(@ModelAttribute("news") NewsDto newsDto) {
        newsServicePort.delete(newsDto.articleId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("comments")
    public List<CommentDto> getComments(@ModelAttribute("news") NewsDto newsDto) {
        return newsDto.comments();
    }

    @PatchMapping("add-comment")
    public ResponseEntity<Void> updateNews(@PathVariable("newsArticle") int newsArticle, @RequestBody CommentDto commentDto) {
            newsServicePort.addComment(newsArticle, commentDto);
            return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> handleNoSuchElementException(NoSuchElementException exception,
                                                                      Locale locale) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                        messageSource.getMessage(exception.getMessage(), new Object[0],
                                exception.getMessage(), locale)));
    }
}
