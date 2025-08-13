package com.msk.ToDoList.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${spring.app.keycloak.admin.client-id}")
    private String clientId;

    @Value("${spring.app.keycloak.admin.client-secret}")
    private String clientSecret;

    @Value("${spring.app.keycloak.realm}")
    private String realm;

    @Value("${spring.app.keycloak.server-url}")
    private String serverUrl;


    // configuring keycloak admin client bean
    @Bean
    public Keycloak keycloak () {
        return KeycloakBuilder.builder()
                .clientSecret(clientSecret)
                .clientId(clientId)
                .grantType("client_credentials")
                .realm(realm)
                .serverUrl(serverUrl)
                .build();
    }
}
