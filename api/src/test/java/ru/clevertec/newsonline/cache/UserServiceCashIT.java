package ru.clevertec.newsonline.cache;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.newsonline.data.UserTestBuilder;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.filter.UserFilter;
import ru.clevertec.newsonline.service.UserService;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest(properties = {
        "spring.cache.type.redis=redis", "spring.data.redis.host=localhost", "spring.data.redis.port=6379"
})
@ActiveProfiles({"redis", "test"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserServiceCashIT {

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

    @Autowired
    private UserService<User, UserFilter> userService;

    @Test
    public void shouldGetUserByIdFromCache() {
        UserTestBuilder ub = UserTestBuilder.builder().build();
        UUID userUUID = ub.getUserUUID();
        long firstCallStart = System.currentTimeMillis();
        Optional<User> userFirstCall = userService.findById(userUUID);
        long firstCallFinish = System.currentTimeMillis();
        long firstDiff = firstCallFinish - firstCallStart;
        long secondCallStart = System.currentTimeMillis();
        Optional<User> userSecondCall = userService.findById(userUUID);
        long secondCallFinish = System.currentTimeMillis();
        long secondDiff = secondCallFinish - secondCallStart;
        Assertions.assertEquals(userFirstCall, userSecondCall);
        Assertions.assertTrue(firstDiff > secondDiff);
    }
}

