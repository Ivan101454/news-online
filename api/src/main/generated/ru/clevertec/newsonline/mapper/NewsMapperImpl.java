package ru.clevertec.newsonline.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.newService.dto.AuthorDto;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.newService.dto.PictureDto;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.enums.Role;
import ru.clevertec.newsonline.newService.enums.Section;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-28T23:33:32+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsDto newsToNewsDto(News news) {
        if ( news == null ) {
            return null;
        }

        UUID newsId = null;
        String headerNews = null;
        int articleId = 0;
        AuthorDto author = null;
        CategoryDto category = null;
        String shortDescription = null;
        String contentLink = null;
        List<CommentDto> comments = null;

        newsId = news.getNewsId();
        headerNews = news.getHeaderNews();
        articleId = news.getArticleId();
        author = authorToAuthorDto( news.getAuthor() );
        category = categoryToCategoryDto( news.getCategory() );
        shortDescription = news.getShortDescription();
        contentLink = news.getContentLink();
        comments = commentListToCommentDtoList( news.getComments() );

        List<PictureDto> pictures = null;
        boolean isPublished = false;

        NewsDto newsDto = new NewsDto( newsId, headerNews, articleId, author, isPublished, category, shortDescription, contentLink, pictures, comments );

        return newsDto;
    }

    @Override
    public News newsDtoToNews(NewsDto newsDto, JpaContext ctx) {
        if ( newsDto == null ) {
            return null;
        }

        News.NewsBuilder news = News.builder();

        news.articleId( newsDto.articleId() );
        news.headerNews( newsDto.headerNews() );
        news.isPublished( newsDto.isPublished() );
        news.shortDescription( newsDto.shortDescription() );
        news.contentLink( newsDto.contentLink() );
        news.comments( commentDtoListToCommentList( newsDto.comments(), ctx ) );

        return news.build();
    }

    @Override
    public CommentDto commentToCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        UUID commentId = null;
        LocalDateTime dateOfComment = null;
        String textComment = null;
        UserDto authorComment = null;

        commentId = comment.getCommentId();
        dateOfComment = comment.getDateOfComment();
        textComment = comment.getTextComment();
        authorComment = userToUserDto( comment.getAuthorComment() );

        CommentDto commentDto = new CommentDto( commentId, dateOfComment, textComment, authorComment );

        return commentDto;
    }

    @Override
    public Comment commentDtoToComment(CommentDto commentDto, JpaContext ctx) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.commentId( commentDto.commentId() );
        comment.dateOfComment( commentDto.dateOfComment() );
        comment.textComment( commentDto.textComment() );
        comment.authorComment( userDtoToUser( commentDto.authorComment() ) );

        return comment.build();
    }

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        String username = null;
        String login = null;
        String password = null;

        username = user.getUsername();
        login = user.getLogin();
        password = user.getPassword();

        Role role = null;
        List<CommentDto> comments = null;

        UserDto userDto = new UserDto( username, login, password, comments, role );

        return userDto;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userDto.username() );
        user.login( userDto.login() );
        user.password( userDto.password() );
        user.comments( commentDtoListToCommentList1( userDto.comments() ) );

        return user.build();
    }

    @Override
    public AuthorDto authorToAuthorDto(Author author) {
        if ( author == null ) {
            return null;
        }

        String nameAuthor = null;
        String lastName = null;
        LocalDateTime dateOfRegistration = null;
        String phoneNumber = null;
        String email = null;

        nameAuthor = author.getNameAuthor();
        lastName = author.getLastName();
        dateOfRegistration = author.getDateOfRegistration();
        phoneNumber = author.getPhoneNumber();
        email = author.getEmail();

        List<NewsDto> writeNews = null;

        AuthorDto authorDto = new AuthorDto( nameAuthor, lastName, dateOfRegistration, phoneNumber, email, writeNews );

        return authorDto;
    }

    @Override
    public Author authorDtoToAuthor(AuthorDto authorDto) {
        if ( authorDto == null ) {
            return null;
        }

        Author.AuthorBuilder author = Author.builder();

        author.nameAuthor( authorDto.nameAuthor() );
        author.lastName( authorDto.lastName() );
        author.phoneNumber( authorDto.phoneNumber() );
        author.email( authorDto.email() );

        return author.build();
    }

    @Override
    public CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        Section section = null;

        section = category.getSection();

        List<NewsDto> newsList = null;

        CategoryDto categoryDto = new CategoryDto( section, newsList );

        return categoryDto;
    }

    @Override
    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.section( categoryDto.section() );

        return category.build();
    }

    protected List<CommentDto> commentListToCommentDtoList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDto> list1 = new ArrayList<CommentDto>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToCommentDto( comment ) );
        }

        return list1;
    }

    protected List<Comment> commentDtoListToCommentList(List<CommentDto> list, JpaContext ctx) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment( commentDto, ctx ) );
        }

        return list1;
    }

    protected Comment commentDtoToComment1(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.commentId( commentDto.commentId() );
        comment.dateOfComment( commentDto.dateOfComment() );
        comment.textComment( commentDto.textComment() );
        comment.authorComment( userDtoToUser( commentDto.authorComment() ) );

        return comment.build();
    }

    protected List<Comment> commentDtoListToCommentList1(List<CommentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment1( commentDto ) );
        }

        return list1;
    }
}
