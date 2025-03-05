package ru.clevertec.newsonline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.clevertec.newsonline.client.RestClientNewsRestClient;

@Configuration
public class ClientBean {

    @Bean
    public RestClientNewsRestClient productsRestClient(
            @Value("${news.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri) {
        return new RestClientNewsRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .build());
    }
}
