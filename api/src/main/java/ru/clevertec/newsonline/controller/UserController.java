package ru.clevertec.newsonline.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newsonline.dto.NewsDto;
import ru.clevertec.newsonline.dto.UserDto;
import ru.clevertec.newsonline.entity.News;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.filter.UserFilter;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.service.UserService;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Transactional
public class UserController {

    private final UserService<User, UserFilter> userService;
    private final NewsMapper INSTANCE;


    @GetMapping("/admin/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable UUID userId) {
        Optional<UserDto> userDto = userService.findById(userId).map(user -> INSTANCE.userToUserDto((User) user));
        return userDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/registration")
    public Optional<News> create(@RequestBody UserDto userDto) {
        return userService.create(INSTANCE.userDtoToUser(userDto));
    }

    @PostMapping("/admin/update/{userId}")
    public void update(@PathVariable UUID userId, @RequestBody UserDto userDTo) {
        userService.update(userId, INSTANCE.userDtoToUser(userDTo));
    }

    @PostMapping("/admin/delete/{userId}")
    public void delete(@PathVariable UUID userId) {
        userService.delete(userId);
    }



}
