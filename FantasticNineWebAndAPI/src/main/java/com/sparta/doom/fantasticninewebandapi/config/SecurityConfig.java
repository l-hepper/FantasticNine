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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest -> authRequest
                        // Public pages
                        .requestMatchers("/", "/welcome/").permitAll()
                        .requestMatchers("/css/**", "/images/**", "/javascript/**").permitAll()
                        .requestMatchers("/**").permitAll()

                        // API
                        .requestMatchers("/api/**").permitAll()

                        // Web
                        .requestMatchers("/movies/**", "/theaters/**", "/users/**").permitAll()
                        .requestMatchers("/movies/create/", "/theaters/create/", "/users/create/").hasRole("ADMIN")
                        .requestMatchers("/movies/{id}/", "/theaters/{id}/", "/users/{id}/").hasRole("ADMIN")
                        .requestMatchers("/movies/{id}/comments/create/").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/movies/{id}/comments/{id}/").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout/")
                        .permitAll()
                )
                .build();
    }

    // dev chain
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(authRequest ->
//                        authRequest.anyRequest().permitAll())
//                .build();
//    }
}
