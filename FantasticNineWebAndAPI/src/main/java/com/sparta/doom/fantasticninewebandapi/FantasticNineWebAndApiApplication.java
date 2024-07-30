package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.UserModel;
import com.sparta.doom.fantasticninewebandapi.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FantasticNineWebAndApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FantasticNineWebAndApiApplication.class, args);
    }
}
