package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
import com.sparta.doom.fantasticninewebandapi.repositories.MoviesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootTest
@EnableMongoRepositories
class MongoDbTestApplicationTests {

	@Autowired
	private MoviesRepository moviesRepository;

	@Test
	void contextLoads() {

		System.out.println("_______");
		for (MoviesModel movie : moviesRepository.findAll()) {
			System.out.println("Movie: " + movie);

		}

	}

}
