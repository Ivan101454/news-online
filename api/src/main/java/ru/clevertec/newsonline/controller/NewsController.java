package ru.clevertec.newsonline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
import ru.clevertec.newsonline.filter.CommentFilter;
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
@Transactional
public class NewsController {

    private final NewsService<News, NewsFilter> newsService;
    private final CommentService<Comment, CommentFilter> commentService;
    private final NewsMapper INSTANCE;

    @GetMapping("find/{newsId}")
    public ResponseEntity<NewsDto> findNewsById(@PathVariable UUID id) {
        Optional<NewsDto> newsDto = newsService.findById(id).map(INSTANCE::newsToNewsDto);
        return newsDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("find/allnews")
    public ResponseEntity<List<NewsDto>> findAllNews(@RequestParam(defaultValue = "1") int pageNumber,
                                                     @RequestParam(defaultValue = "10") int pageSize) {

        List<NewsDto> allNews = newsService.findByPage(pageNumber, pageSize)
                .stream().map(INSTANCE::newsToNewsDto).toList();
        return new ResponseEntity<>(allNews, HttpStatus.OK);
    }

    @PostMapping("edit/create")
    public ResponseEntity<NewsDto> create(@RequestBody NewsDto newsDto) {
        Optional<News> news = newsService.create(INSTANCE.newsDtoToNews(newsDto));
        Optional<NewsDto> createDto = news.map(INSTANCE::newsToNewsDto);
        return createDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("edit/update/{newsId}")
    public ResponseEntity<NewsDto> update(@PathVariable UUID newsId, @RequestBody NewsDto newsDto) {
        News updateNews = newsService.update(newsId, INSTANCE.newsDtoToNews(newsDto));
        Optional<NewsDto> updateDto = Optional.of(updateNews).map(INSTANCE::newsToNewsDto);
        return updateDto.map(x -> new ResponseEntity<>(x, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("edit/delete/{newsId}")
    public ResponseEntity<NewsDto> delete(@PathVariable UUID newsId) {
        newsService.delete(newsId);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PostMapping("/{newsId}/comment/edit/createcomment")
    public ResponseEntity<NewsDto> createComment(@RequestBody CommentDto commentDto, @PathVariable UUID id) {
        Optional<News> byId = newsService.findById(id);
        return byId.map(news -> {
            news.addComment(INSTANCE.commentDtoToComment(commentDto));
            newsService.update(id, news);
            return new ResponseEntity<>(INSTANCE.newsToNewsDto(news), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{newsId}/comment/edit/updatecomment")
    public ResponseEntity<NewsDto> updateComment() {
        return null;
    }

    @PostMapping("/{newsId}/comment/edit/deletecomment")
    public ResponseEntity<NewsDto> deleteComment() {
        return null;
    }

    @GetMapping("/{newsId}/comment/findcomment")
    public ResponseEntity<List<CommentDto>> findCommentByWord(@RequestBody CommentFilter commentFilter,
                                                              @RequestParam(defaultValue = "1") int pageNumber,
                                                              @RequestParam(defaultValue = "10") int pageSize) {

        List<CommentDto> entityByFilter = commentService.findEntityByFilter(commentFilter, Comment.class, pageNumber, pageSize).stream()
                .map(INSTANCE::commentToCommentDto).toList();
        return new ResponseEntity<>(entityByFilter, HttpStatus.OK);
    }

    @GetMapping("find/findnews")
    public ResponseEntity<List<NewsDto>> findNewsByWord(@RequestBody NewsFilter newsFilter,
                                                        @RequestParam(defaultValue = "1") int pageNumber,
                                                        @RequestParam(defaultValue = "10") int pageSize) {
        List<NewsDto> entityByFilter = newsService.findEntityByFilter(newsFilter, News.class, pageNumber, pageSize).stream()
                .map(INSTANCE::newsToNewsDto).toList();
        return new ResponseEntity<>(entityByFilter, HttpStatus.OK);
    }

}
