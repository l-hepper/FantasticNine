package com.sparta.doom.fantasticninewebandapi.repositories;

import com.sparta.doom.fantasticninewebandapi.models.TheaterDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TheatersRepository extends MongoRepository<TheaterDoc, String> {
}
