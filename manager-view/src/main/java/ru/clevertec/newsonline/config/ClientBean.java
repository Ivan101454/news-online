package ru.clevertec.newsonline.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.clevertec.newsonline.client.RestClientNewsRestClient;
import ru.clevertec.newsonline.security.OAuthClientRequestInterceptor;

@Configuration
public class ClientBean {

    @Bean
    public RestClientNewsRestClient productsRestClient(
            @Value("${news.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository oAuth2AuthorizedClientRepository,
            @Value("${news.services.catalogue.registration-id:keycloak}") String registrationId) {
        return new RestClientNewsRestClient(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(
                        new OAuthClientRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        oAuth2AuthorizedClientRepository), registrationId)
                )
                .build());
    }
}
