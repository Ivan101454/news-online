package ru.clevertec.newsonline.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsonline.dto.AuthorDto;
import ru.clevertec.newsonline.dto.CommentDto;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.dto.UserDto;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.User;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mappings({@Mapping(target = "pictures", ignore = true), @Mapping(target = "comments", ignore = true), @Mapping(target = "isPublished", ignore = true)})
    NewsDto newsToNewsDto(News news);
    News newsDtoToNews(NewsDto newsDto);

    CommentDto commentToCommentDto(Comment comment);
    Comment commentDtoToComment(CommentDto commentDto);

    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);

    @Mappings({@Mapping(target = "writeNews", ignore = true)})
    AuthorDto authorToAuthorDto(Author author);
    @Mappings({@Mapping(target = "author", ignore = true)})
    Author authorDtoToAuthor(AuthorDto authorDto);

}
