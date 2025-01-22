package ru.clevertec.newsonline.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.newsonline.data.UserTestBuilder;
import ru.clevertec.newsonline.dto.UserDto;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.mapper.NewsMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserControllerIT {

    @Autowired
    private NewsMapper INSTANCE;
    @Autowired
    private UserController userController;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void findUserById() {
        //given
        UserTestBuilder ub = UserTestBuilder.builder().build();
        User user = ub.buildUser();
        UUID userId = user.getUserId();
        UserDto userDto = ub.createUserDto(INSTANCE);
        //when
        ResponseEntity<UserDto> responseActual = userController.findUserById(userId);

        //then
        assertEquals(HttpStatus.OK, responseActual.getStatusCode());
        assertEquals(userDto, responseActual.getBody());

    }

    @Test
    void create() {
        //given
        UserTestBuilder ub = UserTestBuilder.builder().build();
        User user = ub.buildUser();
        UUID userId = user.getUserId();
        UserDto userDto = ub.createUserDtoNew(INSTANCE);

        //when
        ResponseEntity<UserDto> responseActual = userController.create(userDto);

        //then
        assertEquals(HttpStatus.CREATED, responseActual.getStatusCode());
    }

    @Test
    void update() {
        //given
        UserTestBuilder ub = UserTestBuilder.builder().build();
        User user = ub.buildUser();
        UUID userId = user.getUserId();
        UserDto userDtoNew = ub.createUserDtoNew(INSTANCE);

        //when
        ResponseEntity<UserDto> updateActual = userController.update(userId, userDtoNew);

        //then
        assertEquals(HttpStatus.OK, updateActual.getStatusCode());
        assertEquals(userDtoNew, updateActual.getBody());

    }

    @Test
    void delete() {
        //given
        UserTestBuilder ub = UserTestBuilder.builder().build();
        User user = ub.buildUser();
        UUID userId = user.getUserId();

        //when
        ResponseEntity<Void> deleteResponse = userController.delete(userId);

        //then
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}