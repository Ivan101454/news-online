package ru.clevertec.newsonline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;
import ru.clevertec.newsonline.client.RestClientNewsRestClient;

@Configuration
public class ClientBean {

    @Bean
    public RestClientNewsRestClient partsRestClient(
            @Value("${ivan101454.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
            @Value("${ivan101454.services.catalogue.username}") String catalogueUsername,
            @Value("${ivan101454.services.catalogue.password}") String cataloguePassword) {
        return new RestClientNewsRestClient(RestClient.builder()
                .baseUrl("http://localhost:8081")
                .requestInterceptor(
                        new BasicAuthenticationInterceptor(catalogueUsername, cataloguePassword))
                .build());
    }
}
