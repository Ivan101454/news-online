package ru.clevertec.newsonline.newService.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.filter.UserFilter;
import ru.clevertec.newsonline.newService.service.interfaces.UserPersistencePort;
import ru.clevertec.newsonline.newService.service.interfaces.UserServicePort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public class UserService implements UserServicePort {

    private final UserPersistencePort userPersistencePort;

    public UserService(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }
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

    public void update(String username, UserDto update) {
        try {
            userPersistencePort.findUserByUsername(username).orElseThrow(() -> new NotFoundException("Сущность не найдена по id"));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        userPersistencePort.save(update);
    }

    public void delete(String username) {
        Optional<UserDto> entity = userPersistencePort.findUserByUsername(username);
        entity.ifPresentOrElse(x -> userPersistencePort.delete(x.userId()), () -> {
            throw new NotFoundException("Удаляемая сушность не найдено по id");
        });
    }

    @Override
    public Optional<UserDto> findUserByUsername(String username) {
        return userPersistencePort.findUserByUsername(username);
    }

    @Override
    public List<UserDto> findEntityByFilter(UserFilter filer, Pageable pageable) {
        return userPersistencePort.filterWord(filer, pageable);
    }


}
