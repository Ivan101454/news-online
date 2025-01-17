package ru.clevertec.newsonline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/users/registration").permitAll()
//                        .requestMatchers("/users/admin/**").hasAuthority("ADMIN")
//                        .requestMatchers("/users/admin/update/*").hasAnyAuthority("ADMIN", "SUBSCRIBER")
////                        .requestMatchers("/news/find/**").permitAll()
////                        .requestMatchers("/news/find/*").permitAll()
////                        .requestMatchers("/news/find/allnews").permitAll()
//                        .requestMatchers("/news/*/comment/*").permitAll()
//                        .requestMatchers("/news/*/comment/edit/*").hasAnyAuthority("ADMIN", "JOURNALIST", "SUBSCRIBER")
                        .anyRequest().authenticated()

                )
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance(); // Используйте NoOpPasswordEncoder
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
