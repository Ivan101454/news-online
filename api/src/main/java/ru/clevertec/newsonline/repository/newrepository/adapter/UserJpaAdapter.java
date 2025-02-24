package ru.clevertec.newsonline.repository.newrepository.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;
import ru.clevertec.newsonline.mapper.NewsMapper;
import ru.clevertec.newsonline.newService.dto.UserDto;
import ru.clevertec.newsonline.newService.service.interfaces.UserPersistencePort;
import ru.clevertec.newsonline.repository.newrepository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final NewsMapper newsMapper;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(newsMapper::userToUserDto).toList();
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(newsMapper::userToUserDto);
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        return userRepository.findById(id).map(newsMapper::userToUserDto);
    }

    @Override
    public UserDto save(UserDto userDto) {
        userRepository.save(newsMapper.userDtoToUser(userDto));
        return userDto;
    }

    @Override
    public void delete(UUID id) {
        userRepository.findById(id)
                        .ifPresentOrElse(userRepository::delete, () -> {throw new NoSuchElementException("Нет такого пользователя");});
    }

    @Override
    public Optional<UserDto> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).map(newsMapper::userToUserDto);
    }

    @Override
    public String getUsername(String username) {
        return findUserByUsername(username).orElseThrow().username();
    }

    @Override
    public String getPassword(String username) {
        return findUserByUsername(username).orElseThrow().password();
    }

    @Override
    public GrantedAuthority getRole(String username) {
        return findUserByUsername(username).orElseThrow().role();
    }
}
