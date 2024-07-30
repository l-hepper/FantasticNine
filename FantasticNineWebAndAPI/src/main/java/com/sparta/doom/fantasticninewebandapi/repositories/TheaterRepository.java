package com.sparta.doom.fantasticninewebandapi.repositories;

import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
import com.sparta.doom.fantasticninewebandapi.models.TheaterModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TheaterRepository extends MongoRepository<TheaterModel, String> {

    Optional<TheaterModel> findTheaterModelByTheaterId(int theaterId);
}
