package com.sparta.doom.fantasticninewebandapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authRequest -> authRequest
//                        // Public pages
//                        .requestMatchers("/", "/welcome/").permitAll()
//                        .requestMatchers("/css/**", "/images/**", "/javascript/**").permitAll()
//                        .requestMatchers("/mflix/**").permitAll()
//
//                        // API
//                        .requestMatchers("/api/**").permitAll()
//
//                        // Web
//                        .requestMatchers("/mflix/movies/**", "/mflix/theaters/**", "/mflix/users/**").permitAll()
//                        .requestMatchers("/mflix/movies/create/", "/mflix/theaters/create/", "/mflix/users/create/").hasRole("ADMIN")
//                        .requestMatchers("/mflix/movies/{id}/", "/mflix/theaters/{id}/", "/mflix/users/{id}/").hasRole("ADMIN")
//                        .requestMatchers("/mflix/movies/{id}/comments/create/").hasAnyRole("USER", "ADMIN")
//                        .requestMatchers("/mflix/movies/{id}/comments/{id}/").hasAnyRole("USER", "ADMIN")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login/")
//                        .defaultSuccessUrl("/mflix/", true)
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout/")
//                        .permitAll()
//                )
//                .build();
//    }

    // dev chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest.anyRequest().permitAll())
                .build();
    }
}
