package ru.clevertec.newsonline.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.clevertec.newsonline.dto.AuthorDto;
import ru.clevertec.newsonline.dto.CategoryDto;
import ru.clevertec.newsonline.dto.CommentDto;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.dto.PictureDto;
import ru.clevertec.newsonline.dto.UserDto;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.enums.Role;
import ru.clevertec.newsonline.enums.Section;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-17T16:37:09+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsDto newsToNewsDto(News news) {
        if ( news == null ) {
            return null;
        }

        String headerNews = null;
        AuthorDto author = null;
        LocalDateTime dateOfNews = null;
        CategoryDto category = null;
        String shortDescription = null;
        String contentLink = null;

        headerNews = news.getHeaderNews();
        author = authorToAuthorDto( news.getAuthor() );
        dateOfNews = news.getDateOfNews();
        category = categoryToCategoryDto( news.getCategory() );
        shortDescription = news.getShortDescription();
        contentLink = news.getContentLink();

        List<PictureDto> pictures = null;
        List<CommentDto> comments = null;
        boolean isPublished = false;

        NewsDto newsDto = new NewsDto( headerNews, author, dateOfNews, isPublished, category, shortDescription, contentLink, pictures, comments );

        return newsDto;
    }

    @Override
    public News newsDtoToNews(NewsDto newsDto) {
        if ( newsDto == null ) {
            return null;
        }

        News.NewsBuilder news = News.builder();

        news.headerNews( newsDto.headerNews() );
        news.author( authorDtoToAuthor( newsDto.author() ) );
        news.dateOfNews( newsDto.dateOfNews() );
        news.isPublished( newsDto.isPublished() );
        news.shortDescription( newsDto.shortDescription() );
        news.contentLink( newsDto.contentLink() );

        return news.build();
    }

    @Override
    public CommentDto commentToCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        LocalDateTime dateOfComment = null;
        String textComment = null;
        UserDto authorComment = null;
        NewsDto news = null;

        dateOfComment = comment.getDateOfComment();
        textComment = comment.getTextComment();
        authorComment = userToUserDto( comment.getAuthorComment() );
        news = newsToNewsDto( comment.getNews() );

        CommentDto commentDto = new CommentDto( dateOfComment, textComment, authorComment, news );

        return commentDto;
    }

    @Override
    public Comment commentDtoToComment(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.dateOfComment( commentDto.dateOfComment() );
        comment.textComment( commentDto.textComment() );
        comment.authorComment( userDtoToUser( commentDto.authorComment() ) );
        comment.news( newsDtoToNews( commentDto.news() ) );

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
        List<CommentDto> comments = null;

        username = user.getUsername();
        login = user.getLogin();
        password = user.getPassword();
        comments = commentListToCommentDtoList( user.getComments() );

        Role role = null;

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
        user.comments( commentDtoListToCommentList( userDto.comments() ) );

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
        author.dateOfRegistration( authorDto.dateOfRegistration() );
        author.phoneNumber( authorDto.phoneNumber() );
        author.email( authorDto.email() );
        author.writeNews( newsDtoListToNewsList( authorDto.writeNews() ) );

        return author.build();
    }

    @Override
    public CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        Section section = null;
        List<NewsDto> newsList = null;

        CategoryDto categoryDto = new CategoryDto( section, newsList );

        return categoryDto;
    }

    @Override
    public Category categorDtoToCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.section( categoryDto.section() );
        category.newsList( newsDtoListToNewsList( categoryDto.newsList() ) );

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

    protected List<Comment> commentDtoListToCommentList(List<CommentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment( commentDto ) );
        }

        return list1;
    }

    protected List<News> newsDtoListToNewsList(List<NewsDto> list) {
        if ( list == null ) {
            return null;
        }

        List<News> list1 = new ArrayList<News>( list.size() );
        for ( NewsDto newsDto : list ) {
            list1.add( newsDtoToNews( newsDto ) );
        }

        return list1;
    }
}
