package com.sparta.doom.fantasticninewebandapi.repositories;

import com.sparta.doom.fantasticninewebandapi.models.TheaterModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TheaterRepository extends MongoRepository<TheaterModel, String> {
}
