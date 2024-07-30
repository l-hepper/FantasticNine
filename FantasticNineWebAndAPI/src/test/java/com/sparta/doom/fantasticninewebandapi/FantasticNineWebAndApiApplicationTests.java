package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
import com.sparta.doom.fantasticninewebandapi.repositories.MoviesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;

@SpringBootTest
@EnableMongoRepositories
class MongoDbTestApplicationTests {

	@Autowired
	private MoviesRepository moviesRepository;

	@Test
	void contextLoads() {

		for (MoviesModel movie : moviesRepository.findAll()) {
			System.out.println("_______\n");
			System.out.println("Movie: " + movie.getAwards());

		}

	}

}
