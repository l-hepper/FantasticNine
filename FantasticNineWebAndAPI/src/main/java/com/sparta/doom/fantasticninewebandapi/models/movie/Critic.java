package com.sparta.doom.fantasticninewebandapi.models.movie;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Critic {
    private Integer meter;
    private Integer numReviews;
    private Double rating;

    public Integer getMeter() {
        return meter;
    }

    public void setMeter(Integer meter) {
        this.meter = meter;
    }

    public Integer getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(Integer numReviews) {
        this.numReviews = numReviews;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}