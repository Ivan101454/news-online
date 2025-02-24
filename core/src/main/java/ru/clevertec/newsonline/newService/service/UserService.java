package ru.clevertec.newsonline.newService.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.service.interfaces.UserPersistencePort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserPersistencePort userPersistencePort;

    public UserService(UserPersistencePort userPersistencePort, PasswordEncoder passwordEncoder) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoder = passwordEncoder;
    }
    @Cacheable(value = "byIdCache", key = "#p0")
    public Optional<UserDto> findById(UUID id) {
        Optional<UserDto> entity = userPersistencePort.findById(id);
        entity.orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        return entity;
    }

    public List<UserDto> findAll() {
        return userPersistencePort.findAll();
    }

    public List<UserDto> findByPage(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return userPersistencePort.findAll(pageable).getContent();
    }

    public Optional<UserDto> create(UserDto userDto) {
        userPersistencePort.save(userDto);
        return Optional.ofNullable(userDto);
    }

    public void update(UUID id, UserDto update) {
        try {
            userPersistencePort.findById(id).orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        userPersistencePort.save(update);
    }

    public void delete(UUID id) {
        Optional<UserDto> entity = userPersistencePort.findById(id);
        entity.ifPresentOrElse(x -> userPersistencePort.delete(id), () -> {
            throw new NotFoundException("Удаляемая сушность не найдено по id");
        });
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userPersistencePort.findUserByUsername(username).map(user ->

                User.withUsername(userPersistencePort.getUsername(username))
                        .password(passwordEncoder.encode(userPersistencePort.getPassword(username)))
                        .authorities(Collections.singleton(userPersistencePort.getRole(username)))
                        .build())
        .orElseThrow(() -> new NotFoundException("User не найден: " + username));
    }
}
