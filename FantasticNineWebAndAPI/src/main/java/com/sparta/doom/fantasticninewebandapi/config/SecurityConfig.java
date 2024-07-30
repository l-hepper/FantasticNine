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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authRequest ->
                        //All
                        authRequest.requestMatchers("/", "/welcome").permitAll().requestMatchers("/css/**", "/images/**", "/javascript/**").permitAll()

                                //Auth
                                .requestMatchers("/mflix/**").permitAll()

                                //api
                                .requestMatchers("/api/movies/**", "/api/theaters/**/", "/api/users/**/", "/api/**/search").permitAll()
                                .requestMatchers("/api/movies/{id}", "/api/theaters/{id}", "/api/users/{id}", "/api/movies/{id}/comments/{id}").hasRole("ADMIN")
                                .requestMatchers("/api/movies/create", "/api/theaters/create", "/api/users/create").hasRole("ADMIN")
                                .requestMatchers("/api/movies/{id}/comments/create").hasRole("USER")

                                //web
                                .requestMatchers("/mflix/movies/**", "/mflix/theaters/**/", "/mflix/users/**/", "/mflix/**/search").permitAll()
                                .requestMatchers("/mflix/movies/{id}", "/mflix/theaters/{id}", "/mflix/users/{id}", "/mflix/movies/{id}/comments/{id}").hasRole("ADMIN")
                                .requestMatchers("/mflix/movies/create", "/mflix/theaters/create", "/mflix/users/create").hasRole("ADMIN")
                                .requestMatchers("/mflix/movies/{id}/comments/create").hasRole("USER"))

                .formLogin(formLogin -> formLogin.loginPage("/login/").defaultSuccessUrl("/mflix/", true).permitAll()).logout(logout -> logout.logoutUrl("/logout/").permitAll()).build();
    }
}
