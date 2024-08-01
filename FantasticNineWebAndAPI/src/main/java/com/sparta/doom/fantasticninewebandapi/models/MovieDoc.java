package com.sparta.doom.fantasticninewebandapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movies")
public class MovieDoc {

    @Id
    private String id;
    private String awards;
    private String[] cast;
    private String[] countries;
    private String[] directors;
    private String fullplot;
    private String genres;
    private String imdb;
    private String[] languages;
    private String lastupdated;
    private Integer num_mflix_comments;
    private String plot;
    private String poster;
    private String rated;
    private String released;
    private Integer runtime;
    @Indexed
    private String title;
    private String tomatoes; // Changed to Tomatoes object
    private String type;
    private String[] writers;
    private String year;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String[] getCountries() {
        return countries;
    }

    public void setCountries(String[] countries) {
        this.countries = countries;
    }

    public String[] getDirectors() {
        return directors;
    }

    public void setDirectors(String[] directors) {
        this.directors = directors;
    }

    public String getFullplot() {
        return fullplot;
    }

    public void setFullplot(String fullplot) {
        this.fullplot = fullplot;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String[] getLanguages() {
        return languages;
    }

    public void setLanguages(String[] languages) {
        this.languages = languages;
    }

    public String getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(String lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Integer getNum_mflix_comments() {
        return num_mflix_comments;
    }

    public void setNum_mflix_comments(Integer num_mflix_comments) {
        this.num_mflix_comments = num_mflix_comments;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTomatoes() {
        return tomatoes;
    }

    public void setTomatoes(String tomatoes) {
        this.tomatoes = tomatoes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getWriters() {
        return writers;
    }

    public void setWriters(String[] writers) {
        this.writers = writers;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
