package com.sparta.doom.fantasticninewebandapi.config;

import com.sparta.doom.fantasticninewebandapi.security.api.TokenRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final TokenRequestFilter tokenRequestFilter;

    @Autowired
    public SecurityConfig(TokenRequestFilter tokenRequestFilter) {
        this.tokenRequestFilter = tokenRequestFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http
//            .sessionManagement(sessionManagement ->
//            sessionManagement
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            )
//            .csrf(AbstractHttpConfigurer::disable)
//        .authorizeHttpRequests(authRequest ->
//            authRequest
//                    .requestMatchers("/", "/welcome/", "/home/", "/api/**", "/mflix/**").permitAll()
//                .requestMatchers("/mflix/movies/create/", "/mflix/theaters/create/", "/mflix/users/create/").hasRole("ADMIN")
//                .requestMatchers("/mflix/movies/{id}/comments/create/").hasAnyRole("USER", "ADMIN")
//                .requestMatchers("/mflix/movies/{id}/comments/{id}/").hasAnyRole("USER", "ADMIN")
//                .anyRequest().authenticated()
//        )
//                .formLogin(formLogin ->
//            formLogin
//                    .loginPage("/login/")
//            .defaultSuccessUrl("/mflix/", true)
//                .permitAll()
//        )
//                .logout(logout ->
//            logout
//                    .logoutUrl("/logout/")
//            .permitAll()
//        )
//                .build();
//
//    http
//            .addFilterBefore(new FilterChainProxy(
//            new DefaultSecurityFilterChain(
//            new AntPathRequestMatcher("/api/**"),
//    tokenRequestFilter
//            )
//                    ), UsernamePasswordAuthenticationFilter.class);
//
//    return http.build();
//    }

    // dev chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                .anyRequest().permitAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new FilterChainProxy(
                        new DefaultSecurityFilterChain(
                                new AntPathRequestMatcher("/api/**"),
                                tokenRequestFilter
                        )
                ), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
