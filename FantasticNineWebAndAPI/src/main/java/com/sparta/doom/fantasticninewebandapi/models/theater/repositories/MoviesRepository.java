package com.sparta.doom.fantasticninewebandapi.models.theater.repositories;
import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MoviesRepository extends MongoRepository<MovieDoc, String> {

}
