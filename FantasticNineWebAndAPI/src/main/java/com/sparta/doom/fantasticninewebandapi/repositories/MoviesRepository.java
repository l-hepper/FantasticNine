package com.sparta.doom.fantasticninewebandapi.repositories;
import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends MongoRepository<MoviesModel, String> {
}
