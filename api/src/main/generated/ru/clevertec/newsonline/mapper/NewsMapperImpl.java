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
import ru.clevertec.newsonline.entity.Picture;
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
    date = "2025-03-02T21:30:44+0300",
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
        LocalDateTime dateOfNews = null;
        int articleId = 0;
        String shortDescription = null;
        String contentLink = null;
        List<CommentDto> comments = null;

        newsId = news.getNewsId();
        headerNews = news.getHeaderNews();
        dateOfNews = news.getDateOfNews();
        articleId = news.getArticleId();
        shortDescription = news.getShortDescription();
        contentLink = news.getContentLink();
        comments = commentListToCommentDtoList( news.getComments() );

        List<PictureDto> pictures = null;
        boolean isPublished = false;

        NewsDto newsDto = new NewsDto( newsId, headerNews, dateOfNews, articleId, isPublished, shortDescription, contentLink, pictures, comments );

        return newsDto;
    }

    @Override
    public News newsDtoToNews(NewsDto newsDto, JpaContextNews ctx, JpaContextAuthor ctxA) {
        if ( newsDto == null ) {
            return null;
        }

        News.NewsBuilder news = News.builder();

        news.articleId( newsDto.articleId() );
        news.headerNews( newsDto.headerNews() );
        news.isPublished( newsDto.isPublished() );
        news.shortDescription( newsDto.shortDescription() );
        news.contentLink( newsDto.contentLink() );
        news.comments( commentDtoListToCommentList( newsDto.comments(), ctx, ctxA ) );

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

        commentId = comment.getCommentId();
        dateOfComment = comment.getDateOfComment();
        textComment = comment.getTextComment();

        CommentDto commentDto = new CommentDto( commentId, dateOfComment, textComment );

        return commentDto;
    }

    @Override
    public Comment commentDtoToComment(CommentDto commentDto, JpaContextNews ctx, JpaContextUser ctxU) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.textComment( commentDto.textComment() );

        return comment.build();
    }

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UUID userId = null;
        String username = null;
        String login = null;
        String password = null;
        List<CommentDto> comments = null;
        Role role = null;

        userId = user.getUserId();
        username = user.getUsername();
        login = user.getLogin();
        password = user.getPassword();
        comments = commentListToCommentDtoList( user.getComments() );
        role = user.getRole();

        UserDto userDto = new UserDto( userId, username, login, password, comments, role );

        return userDto;
    }

    @Override
    public User userDtoToUser(UserDto userDto, JpaContextUser ctxU) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userDto.username() );
        user.login( userDto.login() );
        user.password( userDto.password() );
        user.role( userDto.role() );
        user.comments( commentDtoListToCommentList1( userDto.comments(), ctxU ) );

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
        List<NewsDto> writeNews = null;

        nameAuthor = author.getNameAuthor();
        lastName = author.getLastName();
        dateOfRegistration = author.getDateOfRegistration();
        phoneNumber = author.getPhoneNumber();
        email = author.getEmail();
        writeNews = newsListToNewsDtoList( author.getWriteNews() );

        AuthorDto authorDto = new AuthorDto( nameAuthor, lastName, dateOfRegistration, phoneNumber, email, writeNews );

        return authorDto;
    }

    @Override
    public Author authorDtoToAuthor(AuthorDto authorDto, JpaContextAuthor ctxA) {
        if ( authorDto == null ) {
            return null;
        }

        Author.AuthorBuilder author = Author.builder();

        author.nameAuthor( authorDto.nameAuthor() );
        author.lastName( authorDto.lastName() );
        author.phoneNumber( authorDto.phoneNumber() );
        author.email( authorDto.email() );
        author.writeNews( newsDtoListToNewsList( authorDto.writeNews(), ctxA ) );

        return author.build();
    }

    @Override
    public CategoryDto categoryToCategoryDto(Category category) {
        if ( category == null ) {
            return null;
        }

        UUID categoryId = null;
        Section section = null;
        List<NewsDto> newsList = null;

        categoryId = category.getCategoryId();
        section = category.getSection();
        newsList = newsListToNewsDtoList( category.getNewsList() );

        CategoryDto categoryDto = new CategoryDto( categoryId, section, newsList );

        return categoryDto;
    }

    @Override
    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        if ( categoryDto == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.section( categoryDto.section() );
        category.newsList( newsDtoListToNewsList1( categoryDto.newsList() ) );

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

    protected Comment commentDtoToComment1(CommentDto commentDto, JpaContextNews ctx, JpaContextAuthor ctxA) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.commentId( commentDto.commentId() );
        comment.dateOfComment( commentDto.dateOfComment() );
        comment.textComment( commentDto.textComment() );

        return comment.build();
    }

    protected List<Comment> commentDtoListToCommentList(List<CommentDto> list, JpaContextNews ctx, JpaContextAuthor ctxA) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment1( commentDto, ctx, ctxA ) );
        }

        return list1;
    }

    protected Comment commentDtoToComment2(CommentDto commentDto, JpaContextUser ctxU) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.commentId( commentDto.commentId() );
        comment.dateOfComment( commentDto.dateOfComment() );
        comment.textComment( commentDto.textComment() );

        return comment.build();
    }

    protected List<Comment> commentDtoListToCommentList1(List<CommentDto> list, JpaContextUser ctxU) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment2( commentDto, ctxU ) );
        }

        return list1;
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

    protected Picture pictureDtoToPicture(PictureDto pictureDto, JpaContextAuthor ctxA) {
        if ( pictureDto == null ) {
            return null;
        }

        Picture.PictureBuilder picture = Picture.builder();

        picture.pictureId( pictureDto.pictureId() );
        picture.nameOfPicture( pictureDto.nameOfPicture() );
        picture.linkOnPicture( pictureDto.linkOnPicture() );
        picture.news( newsDtoListToNewsList( pictureDto.news(), ctxA ) );

        return picture.build();
    }

    protected List<Picture> pictureDtoListToPictureList(List<PictureDto> list, JpaContextAuthor ctxA) {
        if ( list == null ) {
            return null;
        }

        List<Picture> list1 = new ArrayList<Picture>( list.size() );
        for ( PictureDto pictureDto : list ) {
            list1.add( pictureDtoToPicture( pictureDto, ctxA ) );
        }

        return list1;
    }

    protected Comment commentDtoToComment3(CommentDto commentDto, JpaContextAuthor ctxA) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.commentId( commentDto.commentId() );
        comment.dateOfComment( commentDto.dateOfComment() );
        comment.textComment( commentDto.textComment() );

        return comment.build();
    }

    protected List<Comment> commentDtoListToCommentList2(List<CommentDto> list, JpaContextAuthor ctxA) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment3( commentDto, ctxA ) );
        }

        return list1;
    }

    protected News newsDtoToNews1(NewsDto newsDto, JpaContextAuthor ctxA) {
        if ( newsDto == null ) {
            return null;
        }

        News.NewsBuilder news = News.builder();

        news.newsId( newsDto.newsId() );
        news.articleId( newsDto.articleId() );
        news.headerNews( newsDto.headerNews() );
        news.dateOfNews( newsDto.dateOfNews() );
        news.isPublished( newsDto.isPublished() );
        news.shortDescription( newsDto.shortDescription() );
        news.contentLink( newsDto.contentLink() );
        news.pictures( pictureDtoListToPictureList( newsDto.pictures(), ctxA ) );
        news.comments( commentDtoListToCommentList2( newsDto.comments(), ctxA ) );

        return news.build();
    }

    protected List<News> newsDtoListToNewsList(List<NewsDto> list, JpaContextAuthor ctxA) {
        if ( list == null ) {
            return null;
        }

        List<News> list1 = new ArrayList<News>( list.size() );
        for ( NewsDto newsDto : list ) {
            list1.add( newsDtoToNews1( newsDto, ctxA ) );
        }

        return list1;
    }

    protected Picture pictureDtoToPicture1(PictureDto pictureDto) {
        if ( pictureDto == null ) {
            return null;
        }

        Picture.PictureBuilder picture = Picture.builder();

        picture.pictureId( pictureDto.pictureId() );
        picture.nameOfPicture( pictureDto.nameOfPicture() );
        picture.linkOnPicture( pictureDto.linkOnPicture() );
        picture.news( newsDtoListToNewsList1( pictureDto.news() ) );

        return picture.build();
    }

    protected List<Picture> pictureDtoListToPictureList1(List<PictureDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Picture> list1 = new ArrayList<Picture>( list.size() );
        for ( PictureDto pictureDto : list ) {
            list1.add( pictureDtoToPicture1( pictureDto ) );
        }

        return list1;
    }

    protected Comment commentDtoToComment4(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.commentId( commentDto.commentId() );
        comment.dateOfComment( commentDto.dateOfComment() );
        comment.textComment( commentDto.textComment() );

        return comment.build();
    }

    protected List<Comment> commentDtoListToCommentList3(List<CommentDto> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDto commentDto : list ) {
            list1.add( commentDtoToComment4( commentDto ) );
        }

        return list1;
    }

    protected News newsDtoToNews2(NewsDto newsDto) {
        if ( newsDto == null ) {
            return null;
        }

        News.NewsBuilder news = News.builder();

        news.newsId( newsDto.newsId() );
        news.articleId( newsDto.articleId() );
        news.headerNews( newsDto.headerNews() );
        news.dateOfNews( newsDto.dateOfNews() );
        news.isPublished( newsDto.isPublished() );
        news.shortDescription( newsDto.shortDescription() );
        news.contentLink( newsDto.contentLink() );
        news.pictures( pictureDtoListToPictureList1( newsDto.pictures() ) );
        news.comments( commentDtoListToCommentList3( newsDto.comments() ) );

        return news.build();
    }

    protected List<News> newsDtoListToNewsList1(List<NewsDto> list) {
        if ( list == null ) {
            return null;
        }

        List<News> list1 = new ArrayList<News>( list.size() );
        for ( NewsDto newsDto : list ) {
            list1.add( newsDtoToNews2( newsDto ) );
        }

        return list1;
    }
}
