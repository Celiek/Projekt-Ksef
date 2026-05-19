package com.ksef.security_service.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloackConfig {

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8089")
                .realm("ksef")
                .clientId("admin-cli")
                .username("celiek")
                .password("admin")
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }


}
