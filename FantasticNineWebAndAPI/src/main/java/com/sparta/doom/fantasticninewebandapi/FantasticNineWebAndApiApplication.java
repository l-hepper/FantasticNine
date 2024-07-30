package com.sparta.doom.fantasticninewebandapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class FantasticNineWebAndApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasticNineWebAndApiApplication.class, args);
	}

}
