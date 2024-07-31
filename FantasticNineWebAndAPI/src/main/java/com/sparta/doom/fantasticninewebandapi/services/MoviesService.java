package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.dtos.MovieSummaryDTO;
import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MoviesService {

    private final MoviesRepository moviesRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MoviesService(MoviesRepository moviesRepository, MongoTemplate mongoTemplate) {
        this.moviesRepository = moviesRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Page<MovieDoc> getAllMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return moviesRepository.findAll(pageable);
    }

    public List<MovieDoc> getAllMovies() {
        return moviesRepository.findAll();
    }

    public Optional<MovieDoc> getMovieById(String id) {
        return moviesRepository.findById(id);
    }

    public Optional<MovieDoc> getMovieByTitle(String title) {
        return moviesRepository.findAll().stream()
                .filter(movieDoc -> movieDoc.getTitle().contains(title))
                .findFirst();
    }

    public List<MovieDoc> getMoviesOnly() {
        return moviesRepository.findAll().stream()
                .filter(movieDoc -> "movie".equalsIgnoreCase(movieDoc.getType()))
                .collect(Collectors.toList());
    }

    public List<MovieDoc> getAllSeries() {
        return moviesRepository.findAll().stream()
                .filter(movieDoc -> "series".equalsIgnoreCase(movieDoc.getType()))
                .collect(Collectors.toList());
    }

    public List<MovieDoc> getMoviesByPartialTitle(String title) {
        return moviesRepository.findAll().stream()
                .filter(movieDoc -> movieDoc.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public MovieDoc createMovie(MovieDoc movieDoc) {
        return moviesRepository.save(movieDoc);
    }

    public Optional<MovieDoc> updateMovie(String id, MovieDoc movieDoc) {
        if (moviesRepository.existsById(id)) {
            movieDoc.setId(id);
            return Optional.of(moviesRepository.save(movieDoc));
        } else {
            return Optional.empty();
        }
    }

    public List<String> getAllGenres() {
        return moviesRepository.findAll().stream()
                .filter(movieDoc -> movieDoc.getGenres() != null)
                .flatMap(movieDoc -> Arrays.stream(movieDoc.getGenres().split(",")))
                .map(String::trim)
                .map(String::toLowerCase)
                .distinct()
                .collect(Collectors.toList());
    }

    public boolean deleteMovie(String id) {
        if (moviesRepository.existsById(id)) {
            moviesRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<MovieDoc> getMoviesByGenre(String genre) {
        return moviesRepository.findAll().stream()
                .filter(movieDoc -> movieDoc.getGenres() != null &&
                        Arrays.stream(movieDoc.getGenres().split(","))
                                .map(String::trim)
                                .map(String::toLowerCase)
                                .anyMatch(genre.toLowerCase()::equals))
                .collect(Collectors.toList());
    }

    public List<MovieDoc> getTop10ByImdbRating() {
        Query query = new Query();
        query.addCriteria(Criteria.where("imdb.rating").ne(""));
        query.with(Sort.by(Sort.Order.desc("imdb.rating")));

        List<MovieDoc> allMovies = mongoTemplate
                .find(query.limit(20), MovieDoc.class);

        return allMovies.stream()
                .filter(distinctByKey(MovieDoc::getTitle))
                .limit(10)
                .collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public Optional<MovieSummaryDTO> getMovieSummary(String id) {
        return moviesRepository.findById(id).map(movie ->
                new MovieSummaryDTO(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getPoster(),
                        movie.getGenres(),
                        movie.getLanguages(),
                        movie.getType()
                )
        );
    }
}
