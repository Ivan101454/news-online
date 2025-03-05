package ru.clevertec.newsonline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clevertec.newsonline.client.BadRequestException;
import ru.clevertec.newsonline.client.NewsRestClient;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.enums.Section;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("manager-api/news")
public class NewssController {

    private final NewsRestClient newsRestClient;

    @ModelAttribute
    public void populateModel(Model model) {
        model.addAttribute("section", Section.values());
    }

    @GetMapping("list")
    public String getNewsList(Model model,
                              @RequestParam(name = "headerNews", required = false) String headerNews,
                              @RequestParam(name = "shortDescription", required = false) String shortDescription,
                              @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        List<NewsDto> newsList;
        if (headerNews != null && shortDescription != null) {
            newsList = newsRestClient.findNewsByFilter(headerNews, shortDescription, pageNumber, pageSize);
            model.addAttribute("headerNews", headerNews);
            model.addAttribute("shortDescription", shortDescription);
        } else {
            newsList = newsRestClient.findNewsWithPagination(pageNumber, pageSize);
        }
        model.addAttribute("list", newsList);
        return "catalogue/news/list";
    }

    @GetMapping("create")
    public String getCreateNewsPage() {
        return "catalogue/news/create";
    }

    @PostMapping("create")
    public String createNews(NewsDto newsDto, Model model) {
        try {
            return "redirect:/manager-api/news/%d".formatted(newsDto.articleId());
        } catch (BadRequestException exception) {
            model.addAttribute("news", newsDto);
            model.addAttribute("errors", exception.getErrors());
            return "create";
        }
    }
}
