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
import ru.clevertec.newsonline.dto.UserDto;
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

    /**
     * Метод для нахождения новости по uuid
     * @param newsId uuid переданный url
     * @return ResponseEntity позвращается со стасум OK в случааае успеха,
     * либо NOT_FOUND если новость не найдена
     */
    @GetMapping("find/{newsId}")
    public ResponseEntity<NewsDto> findNewsById(@PathVariable("newsId") UUID newsId) {
        Optional<NewsDto> newsDto = newsService.findById(newsId).map(INSTANCE::newsToNewsDto);
        return newsDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Метод возвращает список новосте с пагинацией
     * @param pageNumber параметр указывающий на страницу, по умолчание равен 1
     * @param pageSize параметр указывающий количество элементов страниц, по умолчанию 10
     * @return ResponseEntity со статусом OK, либо NO_CONTENT если список пуст
     */
    @GetMapping("find/allnews")
    public ResponseEntity<List<NewsDto>> findAllNews(@RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<NewsDto> allNews = newsService.findByPage(pageNumber, pageSize)
                .stream().map(INSTANCE::newsToNewsDto).toList();
        if (allNews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(allNews, HttpStatus.OK);
        }
    }

    /**
     * Метод создает новость.
     * @param newsDto принимает объект newsDto из формы
     * @return возвращает ResponseEntity со статусом Created, если создана
     * либо INTERNAL_SERVER_ERROR если нет
     */
    @PostMapping("edit/create")
    public ResponseEntity<NewsDto> create(@RequestBody NewsDto newsDto) {
        Optional<News> news = newsService.create(INSTANCE.newsDtoToNews(newsDto));
        Optional<NewsDto> createDto = news.map(INSTANCE::newsToNewsDto);
        return createDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Метод обновляет поля существующей новости
     * @param newsId uuid новости, которую нужно изменить
     * @param newsDto объект дто из формы
     * @return ResponseEntity возвращает статус OK в случае успеха, либо
     * INTERNAL_SERVER_ERROR в случае ошибки
     */
    @PostMapping("edit/update/{newsId}")
    public ResponseEntity<NewsDto> update(@PathVariable UUID newsId, @RequestBody NewsDto newsDto) {
        Optional<News> updateNews = newsService.update(newsId, INSTANCE.newsDtoToNews(newsDto));
        Optional<NewsDto> updateDto = updateNews.map(INSTANCE::newsToNewsDto);
        return updateDto.map(x -> new ResponseEntity<>(x, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Метод удаляет новость по uuid
     * @param newsId uuid новости
     * @return ResponseEntity со статусом OK в случает успеха, либо NOT_FOUND
     * если по uuid не удалось найти новость для удаления
     */
    @PostMapping("edit/delete/{newsId}")
    public ResponseEntity<NewsDto> delete(@PathVariable UUID newsId) {
        Optional<News> delete = newsService.delete(newsId);
        return delete.map(deleteNews -> new ResponseEntity<>(INSTANCE.newsToNewsDto(deleteNews), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Метод для нахождения всех комментво у новости
     * @param commentID uuid самого комментария
     * @return ResponseEntity со списком комментов и статусом OK, либо NO_CONTENT
     * если нет комментов
     */
    @GetMapping("/{newsId}/comment/{commentID}")
    public ResponseEntity<List<CommentDto>> findAllCommentByNews(@PathVariable("commentID") UUID commentID) {
        Optional<News> byId = newsService.findById(commentID);
        List<CommentDto> listComment = byId.stream().map(News::getComments).flatMap(List::stream).map(INSTANCE::commentToCommentDto).toList();
        if (listComment.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(listComment, HttpStatus.OK);
        }
    }

    /**
     * Метод находит необходимый коммент uuid новости и uuid коммента
     * @param newsId uuid новости
     * @param commentId uuid комментария
     * @return ResponseEntity со статусом Ok в случае, если комментарий найден,
     * либо NOT_FOUND сли коммент не найден
     */
    @GetMapping("/{newsId}/comment/{commentId}")
    public ResponseEntity<CommentDto> findCommentOfNewsById(@PathVariable("newsId") UUID newsId, @PathVariable("commentId") UUID commentId) {
        Optional<News> newsById = newsService.findById(newsId);
        return newsById.map(n -> n.getComments().stream().filter(c -> c.getCommentId().equals(commentId))
                        .map(INSTANCE::commentToCommentDto).toList().stream().map(Optional::of).toList().getFirst()
                        .map(comment -> new ResponseEntity<>(comment, HttpStatus.OK))).get()
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Метод создающий комментарий у определенной новости
     * @param commentDto dto коммента из формы на сайте
     * @param newsId uuid для новости
     * @return ResponseEntity со статусом OK, если прошло успешно,
     * либо NOT_FOUND если новость не найдена
     */
    @PostMapping("/{newsId}/comment/edit/createcomment")
    public ResponseEntity<NewsDto> createComment(@RequestBody CommentDto commentDto, @PathVariable("newsId") UUID newsId) {
        Optional<News> byId = newsService.findById(newsId);
        return byId.map(news -> {
            news.addComment(INSTANCE.commentDtoToComment(commentDto));
            newsService.update(newsId, news);
            return new ResponseEntity<>(INSTANCE.newsToNewsDto(news), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Метод обновляет комментарий пользователя
     * @param newsId uuid новости, которой принадлежит комментарий
     * @param commentId uuid комментарий, которыйй требуется обновить
     * @param commentDto dto коммента из формы
     * @return ResponseEntity со статусом OK, если прошло успешно,
     * либо INTERNAL_SERVER_ERROR если ошибка
     */
    @PostMapping("/{newsId}/comment/edit/updatecomment/{commentId}")
    public ResponseEntity<NewsDto> updateComment(@PathVariable("newsId") UUID newsId, @PathVariable("commentId") UUID commentId, @RequestBody CommentDto commentDto) {
        Optional<News> newsById = newsService.findById(newsId);
        newsById.ifPresent(news -> news.deleteComment(commentId));
        newsById.ifPresent(news -> news.addComment(INSTANCE.commentDtoToComment(commentDto)));
        return newsById.map(news -> {
            newsService.update(newsId, news);
            return new ResponseEntity<>(INSTANCE.newsToNewsDto(news), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Метод удаляет комментарий из новости
     * @param newsId uuid новости
     * @param commentId uuid коммента
     * @return ResponseEntity со статусом Ok, либо INTERNAL_SERVER_ERROR если
     * произошла ошибка
     */
    @PostMapping("/{newsId}/comment/edit/deletecomment/{commentId}")
    public ResponseEntity<NewsDto> deleteComment(@PathVariable("newsId") UUID newsId, @PathVariable("commentId") UUID commentId) {
        Optional<News> newsById = newsService.findById(newsId);
        newsById.ifPresent(news -> news.deleteComment(commentId));
        Optional<NewsDto> updateDto = newsById.map(INSTANCE::newsToNewsDto);
        return updateDto.map(x -> new ResponseEntity<>(x, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Метод для нахождения коммента по ключевым словам
     * @param commentFilter фильтр-объект из формы
     * @param pageNumber пагинация на страницы, по умолчанию 1
     * @param pageSize размер страницы пагинации, по умолчанию 10
     * @return ResponseEntity, возвращает статус OK, или NO_CONTENT
     * если ничего не найдено
     */
    @GetMapping("/{newsId}/comment/findcomment")
    public ResponseEntity<List<CommentDto>> findCommentByWord(@RequestBody CommentFilter commentFilter,
                                                              @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        List<CommentDto> entityByFilter = commentService.findEntityByFilter(commentFilter, Comment.class, pageNumber, pageSize).stream()
                .map(INSTANCE::commentToCommentDto).toList();
        if (entityByFilter.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(entityByFilter, HttpStatus.OK);
        }
    }

    /**
     * Метод для нахожения новости по словам из текстовых полей, опредленных в фильтре
     * @param newsFilter фильтр-оббъект, смапеный из формы
     * @param pageNumber страницы пагинации, по умолчанию 1
     * @param pageSize количество элементов страницы, по умолчанию 10
     * @return ResponseEntity со статусом Ok, либо NO_CONTENT,
     * если ничего не найдено
     */
    @GetMapping("find/findnews")
    public ResponseEntity<List<NewsDto>> findNewsByWord(@RequestBody NewsFilter newsFilter,
                                                        @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
                                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<NewsDto> entityByFilter = newsService.findEntityByFilter(newsFilter, News.class, pageNumber, pageSize).stream()
                .map(INSTANCE::newsToNewsDto).toList();
        if (entityByFilter.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(entityByFilter, HttpStatus.OK);
        }
    }

}
