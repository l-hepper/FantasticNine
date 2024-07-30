package com.sparta.doom.fantasticninewebandapi.repositories;
import com.sparta.doom.fantasticninewebandapi.models.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoviesRepository extends MongoRepository<Movie, String> {
}
