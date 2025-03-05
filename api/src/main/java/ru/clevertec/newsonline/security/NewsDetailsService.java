package ru.clevertec.newsonline.security;

import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.newsonline.exception.NotFoundException;
import ru.clevertec.newsonline.repository.newrepository.UserRepository;

import java.util.Collections;
//
//@Service
//@RequiredArgsConstructor
//public class NewsDetailsService implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findUserByUsername(username).map(user ->
//
//                        User.withUsername(user.getUsername())
//                                .password(user.getPassword())
//                                .authorities(Collections.singleton(user.getRole()))
//                                .build())
//                .orElseThrow(() -> new NotFoundException("User не найден: " + username));
//    }
//}
