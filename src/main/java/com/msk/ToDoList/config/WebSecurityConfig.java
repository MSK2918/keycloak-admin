package com.msk.ToDoList.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // this is used for role based accessing resources
@RequiredArgsConstructor
public class WebSecurityConfig {


    private final JwtAuthConverter jwtAuthConverter;


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer () {

        return (web) -> {
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/public/**",
                    "/api/v1/users"
            );

            web.ignoring().requestMatchers(
                    HttpMethod.GET,
                    "/public/**"
            );

            web.ignoring().requestMatchers(
                    HttpMethod.DELETE,
                    "public/**",
                    "/api/v1/users/{id}"
            );

            web.ignoring().requestMatchers(
                    HttpMethod.PUT,
                    "public/**",
                    "/api/v1/users/{id}/send-verification-email",
                    "/api/v1/users/forgot-password"
            );

            web.ignoring().requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
            );
        };
    }


    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.anyRequest().authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

}
