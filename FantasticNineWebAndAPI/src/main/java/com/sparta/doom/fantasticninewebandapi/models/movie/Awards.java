package com.sparta.doom.fantasticninewebandapi.models.movie;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "awards")
public class Awards {

    private Integer wins;
    private Integer nominations;
    private String text;

    public Integer getNominations() {
        return nominations;
    }

    public void setNominations(Integer nominations) {
        this.nominations = nominations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }
}
