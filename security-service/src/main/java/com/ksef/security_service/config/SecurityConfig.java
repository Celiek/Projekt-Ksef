package com.ksef.security_service.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {

       return http.
               csrf(ServerHttpSecurity.CsrfSpec::disable)
               .authorizeExchange(auth -> auth
                       .pathMatchers("/api/v1/auth/**").permitAll()
                       .pathMatchers("/actuator/**").permitAll()
                       .anyExchange().authenticated()
               )
               .oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()))
               .build();

    }
}
