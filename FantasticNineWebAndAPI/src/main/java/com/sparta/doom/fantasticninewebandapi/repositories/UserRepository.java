package com.sparta.doom.fantasticninewebandapi.repositories;

import com.sparta.doom.fantasticninewebandapi.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findByEmail(String email);
}
