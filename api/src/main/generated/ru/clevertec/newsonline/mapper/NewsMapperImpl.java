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
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.Picture;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.enums.Section;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-18T21:25:38+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.4 (Amazon.com Inc.)"
)
@Component
public class NewsMapperImpl implements NewsMapper {

    @Override
    public NewsDto newsToNewsDto(News news) {
        if ( news == null ) {
            return null;
        }

        String headerNews = null;
        LocalDateTime dateOfNews = null;
        CategoryDto category = null;
        String shortDescription = null;
        String bodyNews = null;
        List<PictureDto> pictures = null;
        List<CommentDto> comments = null;

        headerNews = news.getHeaderNews();
        dateOfNews = news.getDateOfNews();
        category = categoryToCategoryDto( news.getCategory() );
        shortDescription = news.getShortDescription();
        bodyNews = news.getBodyNews();
        pictures = pictureListToPictureDtoList( news.getPictures() );
        comments = commentListToCommentDtoList( news.getComments() );

        AuthorDto authorNews = null;
        boolean isPublished = false;

        NewsDto newsDto = new NewsDto( headerNews, authorNews, dateOfNews, isPublished, category, shortDescription, bodyNews, pictures, comments );

        return newsDto;
    }

    @Override
    public News newsDtoToNews(NewsDto newsDto) {
        if ( newsDto == null ) {
            return null;
        }

        News.NewsBuilder news = News.builder();

        news.headerNews( newsDto.headerNews() );
        news.dateOfNews( newsDto.dateOfNews() );
        news.isPublished( newsDto.isPublished() );
        news.category( categoryDtoToCategory( newsDto.category() ) );
        news.shortDescription( newsDto.shortDescription() );
        news.bodyNews( newsDto.bodyNews() );
        news.pictures( pictureDtoListToPictureList( newsDto.pictures() ) );
        news.comments( commentDtoListToCommentList( newsDto.comments() ) );

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

    protected List<NewsDto> newsListToNewsDtoList(List<News> list) {
        if ( list == null ) {
            return null;
        }

        List<NewsDto> list1 = new ArrayList<NewsDto>( list.size() );
        for ( News news : list ) {
            list1.add( newsToNewsDto( news ) );
        }

        return list1;
    }

    protected CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        Section section = null;
        List<NewsDto> newsList = null;

        section = category.getSection();
        newsList = newsListToNewsDtoList( category.getNewsList() );

        CategoryDto categoryDto = new CategoryDto( section, newsList );

        return categoryDto;
    }

    protected PictureDto pictureToPictureDto(Picture picture) {
        if ( picture == null ) {
            return null;
        }

        String nameOfPicture = null;
        String linkOnPicture = null;
        List<NewsDto> news = null;

        nameOfPicture = picture.getNameOfPicture();
        linkOnPicture = picture.getLinkOnPicture();
        news = newsListToNewsDtoList( picture.getNews() );

        PictureDto pictureDto = new PictureDto( nameOfPicture, linkOnPicture, news );

        return pictureDto;
    }

    protected List<PictureDto> pictureListToPictureDtoList(List<Picture> list) {
        if ( list == null ) {
            return null;
        }

        List<PictureDto> list1 = new ArrayList<PictureDto>( list.size() );
        for ( Picture picture : list ) {
            list1.add( pictureToPictureDto( picture ) );
        }

        return list1;
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

    protected Category categoryDtoToCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.section( categoryDto.section() );
        category.newsList( newsDtoListToNewsList( categoryDto.newsList() ) );

        return category.build();
    }

    protected Picture pictureDtoToPicture(PictureDto pictureDto) {
        if ( pictureDto == null ) {
            return null;
        }

        Picture.PictureBuilder picture = Picture.builder();

        picture.nameOfPicture( pictureDto.nameOfPicture() );
        picture.linkOnPicture( pictureDto.linkOnPicture() );
        picture.news( newsDtoListToNewsList( pictureDto.news() ) );

        return picture.build();
    }

    protected List<Picture> pictureDtoListToPictureList(List<PictureDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Picture> list1 = new ArrayList<Picture>( list.size() );
        for ( PictureDto pictureDto : list ) {
            list1.add( pictureDtoToPicture( pictureDto ) );
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

    protected UserDto userToUserDto(User user) {
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

        UserDto userDto = new UserDto( username, login, password, comments );

        return userDto;
    }

    protected User userDtoToUser(UserDto userDto) {
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
}
