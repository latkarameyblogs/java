package org.learn.study.azauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll() // Public endpoints
                .requestMatchers("/api/secure").authenticated() // Secured endpoint
                .anyRequest().authenticated() // All other endpoints require authentication
            )
            .oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/home", true) // Redirect after successful login
            );
        return http.build();
    }
}