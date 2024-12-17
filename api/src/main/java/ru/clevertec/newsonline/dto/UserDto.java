package ru.clevertec.newsonline.dto;

import java.util.List;

public record UserDto(String username, String login, String password,
                      List<CommentDto> comments) {
}
