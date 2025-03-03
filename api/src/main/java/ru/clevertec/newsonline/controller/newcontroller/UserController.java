package ru.clevertec.newsonline.controller.newcontroller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.service.interfaces.UserServicePort;

import java.util.Locale;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
@RequestMapping("catalogue-api/users/{username}")
public class UserController {

    private final UserServicePort userServicePort;

    @ModelAttribute("user")
    public UserDto getUser(@PathVariable("username") String username) {
        return userServicePort.findUserByUsername(username).orElseThrow(() -> new NoSuchElementException("catalogue.errors.user.not_found"));
    }

    @GetMapping()
    public UserDto findUser(@ModelAttribute("user") UserDto userDto) {
        return userDto;
    }

    @PatchMapping()
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserDto update,
                                           BindingResult bindingResult, Locale locale) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                throw exception;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            userServicePort.update(update.username(), update);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUser(@ModelAttribute("user") UserDto userDto) {
        userServicePort.delete(userDto.username());
        return ResponseEntity.noContent().build();
    }

}
