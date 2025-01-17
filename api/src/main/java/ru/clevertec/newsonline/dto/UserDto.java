package ru.clevertec.newsonline.dto;

import ru.clevertec.newsonline.enums.Role;

import java.util.List;

public record UserDto(String username, String login, String password,
                      List<CommentDto> comments, Role role) {
}
