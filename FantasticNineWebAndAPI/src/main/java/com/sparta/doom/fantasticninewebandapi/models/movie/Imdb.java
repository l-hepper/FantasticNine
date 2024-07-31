package com.sparta.doom.fantasticninewebandapi.models.movie;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "imdb")
public class Imdb {

    private Integer id;
    private Double rating;
    private Integer votes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
