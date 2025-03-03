package ru.clevertec.newsonline.controller.newcontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.service.interfaces.UserServicePort;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("catalogue-api/users")
public class UsersController {

    private final UserServicePort userServicePort;

    @GetMapping("list")
    public List<UserDto> getUser() {
        return userServicePort.findAll();
    }

    @GetMapping("list-pagination")
    public List<UserDto> findUsersWithPagination(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        return userServicePort.findByPage(pageNumber, pageSize);
    }

    @PostMapping()
    public ResponseEntity<?> createPart(@Valid @RequestBody UserDto userDto,
                                        BindingResult bindingResult,
                                        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Optional<UserDto> news = userServicePort.create(userDto);
            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/catalogue-api/news/list")
                            .build(Map.of("article", userDto.userId())))
                    .body(news);
        }
    }
}

