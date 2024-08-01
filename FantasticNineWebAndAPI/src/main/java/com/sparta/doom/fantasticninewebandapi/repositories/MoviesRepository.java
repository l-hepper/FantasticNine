package com.sparta.doom.fantasticninewebandapi.repositories;
import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;


@Repository
public interface MoviesRepository extends MongoRepository<MovieDoc, String> {

    Stream<MovieDoc> findAllBy();

}
