package com.sparta.doom.fantasticninewebandapi.models.movie;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;

@Document
public class Tomatoes {

    private Critic critic;
    private String dvd;
    private Integer fresh;
    @JsonDeserialize(using = LastUpdatedDeserializer.class)
    private String lastUpdated;
    private String production;
    private Integer rotten;
    private Viewer viewer;
    private String website;

    public Critic getCritic() {
        return critic;
    }

    public void setCritic(Critic critic) {
        this.critic = critic;
    }

    public String getDvd() {
        return dvd;
    }

    public void setDvd(String dvd) {
        this.dvd = dvd;
    }

    public Integer getFresh() {
        return fresh;
    }

    public void setFresh(Integer fresh) {
        this.fresh = fresh;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public Integer getRotten() {
        return rotten;
    }

    public void setRotten(Integer rotten) {
        this.rotten = rotten;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public static class Critic {
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

    public static class Viewer {
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

    public static class LastUpdated {
        private String date;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
    public static class LastUpdatedDeserializer extends JsonDeserializer<String> {
        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            JsonNode node = p.readValueAsTree();
            JsonNode dateNode = node.get("$date");
            return (dateNode != null) ? dateNode.asText() : null;
        }
    }
}