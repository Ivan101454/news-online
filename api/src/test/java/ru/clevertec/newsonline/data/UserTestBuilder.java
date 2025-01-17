package ru.clevertec.newsonline.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.clevertec.newsonline.dto.UserDto;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.enums.Role;
import ru.clevertec.newsonline.mapper.NewsMapper;

import java.util.Optional;
import java.util.UUID;

@Builder
@Data
@RequiredArgsConstructor
public class UserTestBuilder {

    private final NewsMapper newsMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public User buildUser() {
        return User.builder().userId(UUID.fromString("0a77c64d-9dc9-452b-a9b9-7cc983d809c2"))
                .login("Bert@mail.ru").password("Leeniam124").username("Vand").role(Role.ADMIN)
                .build();
    }

    public UUID getUserUUID() {
        return UUID.fromString("0a77c64d-9dc9-452b-a9b9-7cc983d809c2");
    }

    public UserDto createUserDto() {
        return Optional.of(buildUser()).map(newsMapper.INSTANCE::userToUserDto).orElseThrow();
    }

    public String getJsonUserDto() throws JsonProcessingException {
        return objectMapper.writeValueAsString(createUserDto());
    }
}
