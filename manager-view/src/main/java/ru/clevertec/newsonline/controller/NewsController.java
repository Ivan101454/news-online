package ru.clevertec.newsonline.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.clevertec.newsonline.client.BadRequestException;
import ru.clevertec.newsonline.client.NewsRestClient;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.enums.Section;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("manager-api/news/{articleId:\\d+}")
public class NewsController {

    private final NewsRestClient newsRestClient;
    private final MessageSource messageSource;

    @ModelAttribute("news")
    public NewsDto news(@PathVariable("articleId") int articleId, Model model) {
        model.addAttribute("section", Section.values());
        return newsRestClient.findNews(articleId).orElseThrow(() -> new NoSuchElementException("catalogue.errors.product.not_found"));
    }

    @GetMapping()
    public String getNews() {
        return "catalogue/news/article";
    }

    @GetMapping("edit")
    public String getPartEditPage(Model model) {
        return "catalogue/news/edit";
    }

    @PostMapping("edit")
    public String updateNews(@ModelAttribute(name = "news", binding = false) NewsDto news, NewsDto updateNews, Model model) {
        try {
            newsRestClient.updateNews(updateNews);
            return "redirect:/manager-api/news/%d".formatted(news.articleId());
        } catch (BadRequestException exception) {
            model.addAttribute("newsUpdate", news);
            model.addAttribute("errors", exception.getErrors());
            return "catalogue/news/edit";
        }
    }

    @PostMapping("delete")
    public String delete(@ModelAttribute("news") NewsDto news) {
        newsRestClient.deleteNews(news.articleId());
        return "redirect:/manager-api/news/list";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model, HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", messageSource.getMessage(e.getMessage(), new Object[]{}, locale));
        return "errors/404";
    }
}
