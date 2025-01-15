package ru.clevertec.newsonline.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.clevertec.newsonline.entity.User;
import ru.clevertec.newsonline.repository.ServerRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final ServerRepository serverRepository;

    public Optional<User> findUserById(UUID uuid) {
        return serverRepository.findById(uuid);
    }

    public User createUser(User user) {
        return serverRepository.save(user);
    }

    public void deleteUser(UUID uuid) {
        serverRepository.deleteById(uuid);
    }

    public User updateUser(UUID uuid, User user) {
        serverRepository.findById(uuid).orElseThrow();
        return serverRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
