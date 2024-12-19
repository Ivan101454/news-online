package ru.clevertec.newsonline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newsonline.dto.CommentDto;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.filter.NewsFilter;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.service.CommentService;
import ru.clevertec.newsonline.service.NewsService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/news")
public class NewsController {

    private final NewsService<News, NewsFilter> newsService;
    private final NewsMapper INSTANCE;

    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDto> findNewsById(@PathVariable UUID id) {
        Optional<NewsDto> newsDto = newsService.findById(id).map(INSTANCE::newsToNewsDto);
        return newsDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

//        @GetMapping("/allnews")
//        public ResponseEntity<List<NewsDto>> findAll() {
//
//            List<NewsDto> allNews = newsService.findAll()
//                    .stream().map(INSTANCE::newsToNewsDto).toList();
//            return new ResponseEntity<>(allNews, HttpStatus.OK);
//        }

    @GetMapping("/allnews")
    public ResponseEntity<List<NewsDto>> findAllNews(@RequestParam(defaultValue = "1") int pageNumber,
                                                     @RequestParam(defaultValue = "10") int pageSize) {

        List<NewsDto> allNews = newsService.findByPage(pageNumber, pageSize)
                .stream().map(INSTANCE::newsToNewsDto).toList();
        return new ResponseEntity<>(allNews, HttpStatus.OK);
    }

    @PostMapping("/create")
    public Optional<News> create(@RequestBody NewsDto newsDto) {
        return newsService.create(INSTANCE.newsDtoToNews(newsDto));
    }

    @PostMapping("/update")
    public void update(@PathVariable UUID id, @RequestBody NewsDto newsDto) {
        newsService.update(id, INSTANCE.newsDtoToNews(newsDto));
    }

    @PostMapping("/delete")
    public void delete(@PathVariable UUID id) {
        newsService.delete(id);
    }

    @GetMapping("/{newsId}/comment")
    public ResponseEntity<List<CommentDto>> findAllCommentByNews(@PathVariable UUID id) {
        Optional<News> byId = newsService.findById(id);
        List<CommentDto> list = byId.stream().map(News::getComments).flatMap(List::stream).map(INSTANCE::commentToCommentDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{newsId}/comment/{commentsId}")
    public ResponseEntity<CommentDto> findCommentOfNewsById(@PathVariable UUID newsid, @PathVariable UUID commentid) {
        Optional<News> byId = newsService.findById(newsid);

        if (byId.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return byId.map(n -> n.getComments().stream().filter(c -> c.getCommentId().equals(commentid))
                        .map(INSTANCE::commentToCommentDto).toList().stream().map(Optional::of).toList().getFirst()
                        .map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))).get()
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
