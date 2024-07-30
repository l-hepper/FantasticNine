package com.sparta.doom.fantasticninewebandapi.repositories;
import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoviesRepository extends MongoRepository<MovieDoc, String> {
}
