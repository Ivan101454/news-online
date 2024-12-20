package ru.clevertec.newsonline.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.clevertec.newsonline.dto.CommentDto;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.entity.Comment;
import ru.clevertec.newsonline.entity.News;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mappings({@Mapping(target = "pictures", ignore = true), @Mapping(target = "comments", ignore = true)})
    NewsDto newsToNewsDto(News news);
    News newsDtoToNews(NewsDto newsDto);

    CommentDto commentToCommentDto(Comment comment);
    Comment commentDtoToComment(CommentDto commentDto);
}
