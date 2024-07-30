package com.sparta.doom.fantasticninewebandapi.repositories;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TheaterRepository extends MongoRepository<TheaterDoc, String> {

    Optional<TheaterDoc> findTheaterModelByTheaterId(int theaterId);
}
