package ru.clevertec.newsonline.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.newsonline.data.CommentTestBuilder;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.filter.CommentFilter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CommentRepositoryIT {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void findCommentById() {

        //given
        Comment expectComment = CommentTestBuilder.builder().build().createComment();

        //when
        Comment actualComment = ((CrudRepository<Comment, UUID>) commentRepository).findById(expectComment.getCommentId()).orElseThrow();

        //then
        assertEquals(expectComment.getCommentId(), actualComment.getCommentId());
    }

    @Test
    public void findAllComments() {

        //given
        int expectSize = 10;

        //when
        int actualSize = commentRepository.findAll().size();
        //then
        assertEquals(expectSize, actualSize);
    }

    @Test
    public void findAllWithPagination() {

        //given
        Pageable pageRequest = PageRequest.of(0, 5);
        CommentTestBuilder cb = CommentTestBuilder.builder().build();
        Comment commentFirst = cb.createComment();
        Comment commentFifth = cb.createCommentFifth();
        //when
        List<Comment> comments = commentRepository.findAll(pageRequest).getContent();

        //then
        assertFalse(comments.isEmpty());
        assertEquals(5, comments.size());
        assertEquals(commentFirst.getCommentId(), comments.stream().filter(x -> x.getCommentId().equals(commentFirst.getCommentId())).toList().getFirst().getCommentId());
        assertEquals(commentFifth.getCommentId(), comments.stream().filter(x -> x.getCommentId().equals(commentFifth.getCommentId())).toList().getFirst().getCommentId());
    }

    @Test
    public void shouldSaveComment() {

        //given
        Comment newComment = CommentTestBuilder.builder().build().createNewComment();

        //when
        commentRepository.save(newComment);
        Optional<Comment> byId = ((CrudRepository<Comment, UUID>) commentRepository).findById(newComment.getCommentId());

        //then
        assertTrue(byId.isPresent());
        assertEquals(newComment.getTextComment(), byId.get().getTextComment());
    }

    @Test
    public void shouldDeleteComment() {
        //given
        Comment comment = CommentTestBuilder.builder().build().createComment();

        //when
        commentRepository.delete(comment);
        Optional<Comment> byId = ((CrudRepository<Comment, UUID>) commentRepository).findById(comment.getCommentId());

        //then
        assertFalse(byId.isPresent());
    }

    @Test
    public void shouldFindCommentByKeyWord() {
        //given
        CommentTestBuilder cb = CommentTestBuilder.builder().build();
        CommentFilter commentFilter = cb.createCommentFilter();
        Comment comment = cb.createComment();

        //when
        List<Comment> comments = commentRepository.filterWord(commentFilter, Comment.class, 0, 5);

        //then
        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
        assertEquals(comment.getTextComment(), comments.getFirst().getTextComment());
    }

}
