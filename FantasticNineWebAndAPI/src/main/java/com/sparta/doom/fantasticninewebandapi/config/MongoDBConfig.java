package com.sparta.doom.fantasticninewebandapi.config;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.domain.Sort;

@Configuration
public class MongoDBConfig {

    private final MongoTemplate mongoTemplate;

    public MongoDBConfig(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @PostConstruct
    public void createIndexes() {
        IndexOperations indexOps = mongoTemplate.indexOps(MovieDoc.class);

        indexOps.ensureIndex(new Index().on("title", Sort.Direction.ASC));
    }
}
