package com.sparta.doom.fantasticninewebandapi.security.api;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "api_keys")
public class ApiKeyModel {
    @Id
    private String id;

    @Field("apiKey")
    private String key;

    @Field("access_level")
    private String accessLevel;

    public String getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
