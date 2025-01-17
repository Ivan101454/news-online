package ru.clevertec.newsonline.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsonline.dto.AuthorDto;
import ru.clevertec.newsonline.dto.CategoryDto;
import ru.clevertec.newsonline.dto.CommentDto;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.dto.UserDto;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.User;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mappings({@Mapping(target = "pictures", ignore = true), @Mapping(target = "comments", ignore = true), @Mapping(target = "isPublished", ignore = true)})
    NewsDto newsToNewsDto(News news);
    @Mappings({@Mapping(target = "pictures", ignore = true), @Mapping(target = "comments", ignore = true), @Mapping(target = "category", ignore = true), @Mapping(target = "newsId", ignore = true)})
    News newsDtoToNews(NewsDto newsDto);

    CommentDto commentToCommentDto(Comment comment);
    @Mappings({@Mapping(target = "commentId", ignore = true)})
    Comment commentDtoToComment(CommentDto commentDto);

    @Mappings({@Mapping(target = "role", ignore = true)})
    UserDto userToUserDto(User user);
    @Mappings({@Mapping(target = "userId", ignore = true), @Mapping(target = "role", ignore = true)})
    User userDtoToUser(UserDto userDto);

    @Mappings({@Mapping(target = "writeNews", ignore = true)})
    AuthorDto authorToAuthorDto(Author author);
    @Mappings({@Mapping(target = "authorId", ignore = true)})
    Author authorDtoToAuthor(AuthorDto authorDto);

    @Mappings({@Mapping(target = "section", ignore = true)})
    CategoryDto categoryToCategoryDto(Category category);
    @Mappings({@Mapping(target = "categoryId", ignore = true)})
    Category categorDtoToCategory(CategoryDto categoryDto);
}
