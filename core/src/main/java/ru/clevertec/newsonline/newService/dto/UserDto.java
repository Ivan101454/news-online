package ru.clevertec.newsonline.newService.dto;


import ru.clevertec.newsonline.newService.enums.Role;

import java.util.List;

public record UserDto(String username, String login, String password,
                      List<CommentDto> comments, Role role) {
}
