package com.sparta.doom.fantasticninewebandapi.models.movie;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tomatoes")
public class Tomatoes {

    private Critic critic;
    private String dvd;
    private Integer fresh;
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
}
