package ru.clevertec.newsonline.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsonline.newService.dto.AuthorDto;
import ru.clevertec.newsonline.newService.dto.CategoryDto;
import ru.clevertec.newsonline.newService.dto.CommentDto;
import ru.clevertec.newsonline.newService.dto.NewsDto;
import ru.clevertec.newsonline.entity.Author;
import ru.clevertec.newsonline.entity.Category;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.newService.dto.UserDto;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

//    @Mappings({@Mapping(target = "pictures", ignore = true), @Mapping(target = "section", ignore = true)})
    NewsDto newsToNewsDto(News news);

    @Mappings({@Mapping(target = "pictures", ignore = true), @Mapping(target = "category", ignore = true), @Mapping(target = "author", ignore = true), @Mapping(target = "dateOfNews", ignore = true), @Mapping(target = "newsId", ignore = true)})
    News newsDtoToNews(NewsDto newsDto, @Context JpaContextNews ctx, @Context JpaContextAuthor ctxA);

    CommentDto commentToCommentDto(Comment comment);

    @Mappings({@Mapping(target = "news", ignore = true), @Mapping(target = "commentId", ignore = true), @Mapping(target = "dateOfComment", ignore = true)})
    Comment commentDtoToComment(CommentDto commentDto, @Context JpaContextNews ctx, @Context JpaContextUser ctxU);

    UserDto userToUserDto(User user);

    @Mappings({@Mapping(target = "userId", ignore = true)})
    User userDtoToUser(UserDto userDto, @Context JpaContextUser ctxU);

    AuthorDto authorToAuthorDto(Author author);

    @Mappings({@Mapping(target = "authorId", ignore = true), @Mapping(target = "dateOfRegistration", ignore = true)})
    Author authorDtoToAuthor(AuthorDto authorDto, @Context JpaContextAuthor ctxA);

    CategoryDto categoryToCategoryDto(Category category);

    @Mappings({@Mapping(target = "categoryId", ignore = true), @Mapping(target = "newsList", ignore = true)})
    Category categoryDtoToCategory(CategoryDto categoryDto, @Context JpaContextNewsCategory ctxNC);
}
