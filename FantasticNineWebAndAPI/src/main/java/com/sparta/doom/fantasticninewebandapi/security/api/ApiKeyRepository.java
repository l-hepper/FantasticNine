package com.sparta.doom.fantasticninewebandapi.security.api;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiKeyRepository extends MongoRepository<ApiKeyModel, String> {
    ApiKeyModel findByKey(String apiKey);
}
