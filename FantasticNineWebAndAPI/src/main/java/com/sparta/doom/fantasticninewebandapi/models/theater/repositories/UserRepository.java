package com.sparta.doom.fantasticninewebandapi.models.theater.repositories;

import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDoc, String> {
    Optional<UserDoc> findByEmail(String email);
}
