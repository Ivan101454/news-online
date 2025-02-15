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

    /**
     * Метод для нахождения пользователя по uuid
     * @param userId uuid пользователя
     * @return ResponseEntity с UserDto
     */
    @GetMapping("/admin/{userId}")
    public ResponseEntity<UserDto> findUserById(@PathVariable UUID userId) {
        Optional<UserDto> userDto = userService.findById(userId).map(user -> INSTANCE.userToUserDto((User) user));
        return userDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK)).
                orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Метод для создания(регистрации) нового пользователя
     * @param userDto объект, сформированный из формы в dto
     * @return ResponseEntity со статусом CREATED в случае успешного созания,
     * либо INTERNAL_SERVER_ERROR в случае ошибок
     */
    @PostMapping("/registration")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        Optional<User> user = userService.create(INSTANCE.userDtoToUser(userDto));
        return user.map(user1 -> new ResponseEntity<>(userDto, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Метод для полей изменения сущности User
     * @param userId uuid пользователя, которого будем изменять
     * @param userDto объект, смапеный из формы в dto
     * @return ResponseEntity со статусом CREATED в случае успешного создания,
     *      * либо INTERNAL_SERVER_ERROR в случае ошибок
     */
    @PostMapping("/admin/update/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable(value = "userId") UUID userId, @RequestBody UserDto userDto) {
        Optional<User> updateUser = userService.update(userId, INSTANCE.userDtoToUser(userDto));
        return updateUser.map(user1 -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    /**
     * Метод, который удаляет сущность по uuid
     * @param userId uuid удаляемой сущности
     * @return возвращает ResponseEntity со статусом OK  в случае успеха,
     * и NOT_FOUND в случае если не найдена
     */
    @PostMapping("/admin/delete/{userId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId) {
        Optional<User> delete = userService.delete(userId);
        return delete.map(deleteUser -> new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                .orElseGet(() -> new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
    }
}
