package ru.clevertec.newsonline.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.wiremock.spring.EnableWireMock;
import ru.clevertec.newsonline.data.UserTestBuilder;
import ru.clevertec.newsonline.dto.UserDto;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.filter.UserFilter;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.service.UserService;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@EnableWireMock
class UserControllerTest {

    @Mock
    private UserService<User, UserFilter> userService;
    @Mock
    private NewsMapper INSTANCE;
    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findUserById() throws JsonProcessingException {
        UserTestBuilder ub = UserTestBuilder.builder().build();
        UUID userUUID = ub.getUserUUID();
        User user = ub.buildUser();
        UserDto userDto = ub.createUserDto(INSTANCE);
        String jsonUserDto = ub.getJsonUserDto(objectMapper, INSTANCE);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/user/admin/" + userUUID))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonUserDto)));

        when(userService.findById(userUUID))
                .thenReturn(Optional.of(user));
        when(INSTANCE.userToUserDto(user))
                .thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.findUserById(userUUID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}