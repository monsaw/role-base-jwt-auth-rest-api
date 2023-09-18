package com.hommies.userauthwithjwt.config;


import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.hommies.userauthwithjwt.model.Permission.*;
import static com.hommies.userauthwithjwt.model.Role.ADMIN;
import static com.hommies.userauthwithjwt.model.Role.MANAGER;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                csrf(AbstractHttpConfigurer::disable).
                cors(AbstractHttpConfigurer::disable).
                authorizeHttpRequests(auth -> {
                            auth.requestMatchers("/api/v1/auth/**").permitAll();

                            // I replaced this with annotation, check controller and also @EnableMethodSecurity here.


                            // new addition while implementing role_base
//                            auth.requestMatchers("/api/v1/management/**").
//                                    hasAnyRole(ADMIN.name(), MANAGER.name());
//
//                            auth.requestMatchers(HttpMethod.GET,"/api/v1/management/**").
//                                    hasAnyAuthority(ADMIN_READ.name(), MANAGEMENT_READ.name());
//                            auth.requestMatchers(HttpMethod.POST,"/api/v1/management/**").
//                                    hasAnyAuthority(ADMIN_CREATE.name(), MANAGEMENT_CREATE.name());
//                            auth.requestMatchers(HttpMethod.PUT,"/api/v1/management/**").
//                                    hasAnyAuthority(ADMIN_UPDATE.name(), MANAGEMENT_UPDATE.name());
//                            auth.requestMatchers(HttpMethod.DELETE,"/api/v1/management/**").
//                                    hasAnyAuthority(ADMIN_DELETE.name(), MANAGEMENT_DELETE.name());
////
//
                            auth.requestMatchers("/api/v1/admin/**").
                                    hasRole(ADMIN.name());

                            auth.requestMatchers(HttpMethod.GET,"/api/v1/admin/**").
                                    hasAuthority(ADMIN_READ.name());
                            auth.requestMatchers(HttpMethod.POST,"/api/v1/admin/**").
                                    hasAuthority(ADMIN_CREATE.name());
                            auth.requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").
                                    hasAuthority(ADMIN_UPDATE.name());
                            auth.requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").
                                    hasAuthority(ADMIN_DELETE.name());

                            // stops here..
                            auth.anyRequest().authenticated();
                }
                ).
                sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

        .build();
    }
}
