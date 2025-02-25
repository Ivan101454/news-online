package ru.clevertec.newsonline.newService.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.clevertec.newsonline.newService.enums.Role;

import java.util.List;

public record UserDto(
        @NotBlank(message = "{catalogue.errors.user.name_is_invalid}")
        String username,
        @NotBlank(message = "{catalogue.errors.user.login_is_invalid}")
        String login,
        @NotBlank(message = "{catalogue.errors.user.password_is_invalid}")
        String password,
        List<CommentDto> comments,
        @NotNull(message = "{catalogue.errors.user.role_is_invalid}")
        Role role) {
}
