package ru.clevertec.newsonline.controller.newcontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.service.interfaces.NewsServicePort;

import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("catalogue-api/news/{newsArticle:\\d+}")
public class NewsController {

    private final NewsServicePort newsServicePort;

    @ModelAttribute("news")
    public NewsDto getProduct(@PathVariable("newsArticle") int newsArticle) {
        return newsServicePort.findByArticleId(newsArticle).orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
    }

    @GetMapping()
    public NewsDto findNews(@ModelAttribute("news") NewsDto newsDto) {
        return newsDto;
    }

    @PatchMapping()
    public ResponseEntity<Void> updateNews(@Valid @RequestBody NewsDto update,
                                            BindingResult bindingResult, Locale locale) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            newsServicePort.update(update.articleId(), update);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteNews(@ModelAttribute("news") NewsDto newsDto) {
        newsServicePort.delete(newsDto.articleId());
        return ResponseEntity.noContent().build();
    }
}
