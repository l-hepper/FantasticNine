package com.sparta.doom.fantasticninewebandapi.security.api;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiKeyRepository extends MongoRepository<ApiKeyModel, ObjectId> {
    Optional<ApiKeyModel> findByKey(String apiKey);
}
