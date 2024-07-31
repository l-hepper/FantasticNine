package com.sparta.doom.fantasticninewebandapi.models.theater.repositories;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface TheaterRepository extends MongoRepository<TheaterDoc, String> {
    Optional<TheaterDoc> findTheaterModelByTheaterId(int theaterId);
}
