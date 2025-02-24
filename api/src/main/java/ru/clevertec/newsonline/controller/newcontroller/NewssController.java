package ru.clevertec.newsonline.controller.newcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.service.interfaces.NewsServicePort;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("catalogue-api/news")
public class NewssController {

    private final NewsServicePort newsServicePort;

    @GetMapping()
    public List<NewsDto> findParts() {
        return newsServicePort.findAll();
    }

}
