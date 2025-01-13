package ru.clevertec.newsonline.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.serviceinteface.IFilterRepository;
import ru.clevertec.newsonline.serviceinteface.IRepository;
import ru.clevertec.newsonline.serviceinteface.IUserRepository;

import java.util.Collections;

public class UserService<E, F> extends CrudService<E, F> implements UserDetailsService {

    private final IRepository<E> userRepository;
    private final IFilterRepository<E, F> iFilterRepository;
    private final IUserRepository<E> iUserRepository;

    public UserService(IRepository<E> userRepository, IFilterRepository<E, F> iFilterRepository, IUserRepository<E> iUserRepository) {
        super(userRepository, iFilterRepository);
        this.userRepository = userRepository;
        this.iFilterRepository = iFilterRepository;
        this.iUserRepository = iUserRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return iUserRepository.findUserByUsername(username).map(user -> new User(
                iUserRepository.getUsername(username),
                iUserRepository.getPassword(username),
                Collections.singleton(iUserRepository.getRole(username))
        )).orElseThrow(() -> new NotFoundException("User не найден: " + username));
    }
}
