package ru.clevertec.newsonline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequest -> authorizeHttpRequest
                        .requestMatchers(HttpMethod.POST, "catalogue-api/news")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.PATCH, "catalogue-api/news/{articleId:\\d+}")
                        .hasAnyAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.PATCH, "catalogue-api/news/{articleId:\\d+}/add-comment")
                        .hasAnyAuthority("SCOPE_edit_catalogue", "SCOPE_view_catalogue")
                        .requestMatchers(HttpMethod.DELETE, "catalogue-api/news/{articleId:\\d+}")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.GET)
                        .hasAnyAuthority("SCOPE_edit_catalogue", "SCOPE_view_catalogue")
                )
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults()))
                .build();
    }
}
