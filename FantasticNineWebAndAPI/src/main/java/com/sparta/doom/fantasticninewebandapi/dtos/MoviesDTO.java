package com.sparta.doom.fantasticninewebandapi.dtos;

import java.util.Arrays;

public class MoviesDTO {

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
    private Integer numMflixComments;
    private String plot;
    private String poster;
    private String rated;
    private String released;
    private Integer runtime;
    private String title;
    private String tomatoes;
    private String type;
    private String[] writers;
    private String year;

    public MoviesDTO() {
    }

    public MoviesDTO(String id, String awards, String[] cast, String[] countries, String[] directors, String fullplot, String genres, String imdb, String[] languages, String lastupdated, Integer numMflixComments, String plot, String poster, String rated, String released, Integer runtime, String title, String tomatoes, String type, String[] writers, String year) {
        this.id = id;
        this.awards = awards;
        this.cast = cast;
        this.countries = countries;
        this.directors = directors;
        this.fullplot = fullplot;
        this.genres = genres;
        this.imdb = imdb;
        this.languages = languages;
        this.lastupdated = lastupdated;
        this.numMflixComments = numMflixComments;
        this.plot = plot;
        this.poster = poster;
        this.rated = rated;
        this.released = released;
        this.runtime = runtime;
        this.title = title;
        this.tomatoes = tomatoes;
        this.type = type;
        this.writers = writers;
        this.year = year;
    }

    // Getters and Setters

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

    public Integer getNumMflixComments() {
        return numMflixComments;
    }

    public void setNumMflixComments(Integer numMflixComments) {
        this.numMflixComments = numMflixComments;
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

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id='" + id + '\'' +
                ", awards='" + awards + '\'' +
                ", cast=" + Arrays.toString(cast) +
                ", countries=" + Arrays.toString(countries) +
                ", directors=" + Arrays.toString(directors) +
                ", fullplot='" + fullplot + '\'' +
                ", genres='" + genres + '\'' +
                ", imdb='" + imdb + '\'' +
                ", languages=" + Arrays.toString(languages) +
                ", lastupdated='" + lastupdated + '\'' +
                ", numMflixComments=" + numMflixComments +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                ", rated='" + rated + '\'' +
                ", released='" + released + '\'' +
                ", runtime=" + runtime +
                ", title='" + title + '\'' +
                ", tomatoes='" + tomatoes + '\'' +
                ", type='" + type + '\'' +
                ", writers=" + Arrays.toString(writers) +
                ", year='" + year + '\'' +
                '}';
    }
}
