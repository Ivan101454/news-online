package ru.clevertec.newsonline.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;
import ru.clevertec.newsonline.serviceinteface.IRepository;
import ru.clevertec.newsonline.serviceinteface.IUserRepository;

import java.util.Collections;

public class UserService<E, F> extends CrudService<E, F> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private final IRepository<E> userRepository;
    private final IFilterRepository<E, F> iFilterRepository;
    private final IUserRepository<E> iUserRepository;

    public UserService(PasswordEncoder passwordEncoder, IRepository<E> userRepository, IFilterRepository<E, F> iFilterRepository, IUserRepository<E> iUserRepository) {
        super(userRepository, iFilterRepository);
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.iFilterRepository = iFilterRepository;
        this.iUserRepository = iUserRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findUserByUsername(username).map(user ->

                User.withUsername(iUserRepository.getUsername(username))
                        .password(passwordEncoder.encode(iUserRepository.getPassword(username)))
                        .authorities(Collections.singleton(iUserRepository.getRole(username)))
                        .build())
        .orElseThrow(() -> new NotFoundException("User не найден: " + username));
    }

}
