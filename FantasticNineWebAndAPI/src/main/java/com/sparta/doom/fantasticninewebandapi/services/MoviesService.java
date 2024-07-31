package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.dtos.MoviesDTO;
import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.models.theater.repositories.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//TODO Add Pagination
@Service
public class MoviesService {

    private final MoviesRepository moviesRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public MoviesService(MoviesRepository moviesRepository, MongoTemplate mongoTemplate) {
        this.moviesRepository = moviesRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public MoviesDTO convertToDto(MovieDoc movieDoc) {
        return new MoviesDTO(
                movieDoc.getId(),
                movieDoc.getAwards(),
                movieDoc.getCast(),
                movieDoc.getCountries(),
                movieDoc.getDirectors(),
                movieDoc.getFullplot(),
                movieDoc.getGenres(),
                movieDoc.getImdb(),
                movieDoc.getLanguages(),
                movieDoc.getLastupdated(),
                movieDoc.getNum_mflix_comments(),
                movieDoc.getPlot(),
                movieDoc.getPoster(),
                movieDoc.getRated(),
                movieDoc.getReleased(),
                movieDoc.getRuntime(),
                movieDoc.getTitle(),
                movieDoc.getTomatoes(),
                movieDoc.getType(),
                movieDoc.getWriters(),
                movieDoc.getYear()
        );
    }

    private MovieDoc convertToEntity(MoviesDTO movieDto) {
        MovieDoc movieDoc = new MovieDoc();
        movieDoc.setId(movieDto.getId());
        movieDoc.setAwards(movieDto.getAwards());
        movieDoc.setCast(movieDto.getCast());
        movieDoc.setCountries(movieDto.getCountries());
        movieDoc.setDirectors(movieDto.getDirectors());
        movieDoc.setFullplot(movieDto.getFullplot());
        movieDoc.setGenres(movieDto.getGenres());
        movieDoc.setImdb(movieDto.getImdb());
        movieDoc.setLanguages(movieDto.getLanguages());
        movieDoc.setLastupdated(movieDto.getLastupdated());
        movieDoc.setNum_mflix_comments(movieDto.getNumMflixComments());
        movieDoc.setPlot(movieDto.getPlot());
        movieDoc.setPoster(movieDto.getPoster());
        movieDoc.setRated(movieDto.getRated());
        movieDoc.setReleased(movieDto.getReleased());
        movieDoc.setRuntime(movieDto.getRuntime());
        movieDoc.setTitle(movieDto.getTitle());
        movieDoc.setTomatoes(movieDto.getTomatoes());
        movieDoc.setType(movieDto.getType());
        movieDoc.setWriters(movieDto.getWriters());
        movieDoc.setYear(movieDto.getYear());
        return movieDoc;
    }

    public List<MoviesDTO> getAllMovies() {
        List<MovieDoc> movieDocs = moviesRepository.findAll();
        return movieDocs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MoviesDTO getMovieById(String id) {
        Optional<MovieDoc> movie = moviesRepository.findById(id);
        return movie.map(this::convertToDto).orElse(null);
    }

    public Optional<MovieDoc> getMovieByTitle(String title) {
        return moviesRepository.findAll().stream()
                .filter(movieDoc -> movieDoc.getTitle().contains(title))
                .findFirst();
    }

    public List<MovieDoc> getMoviesByPartialTitle(String title) {
        return moviesRepository.findAll().stream()
                .filter(movieDoc -> movieDoc.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public MoviesDTO createMovie(MoviesDTO movieDto) {
        MovieDoc movieDoc = convertToEntity(movieDto);
        MovieDoc savedMovieDoc = moviesRepository.save(movieDoc);
        return convertToDto(savedMovieDoc);
    }

    public MoviesDTO updateMovie(String id, MoviesDTO movieDto) {
        if (moviesRepository.existsById(id)) {
            MovieDoc movieDoc = convertToEntity(movieDto);
            movieDoc.setId(id);
            MovieDoc updatedMovieDoc = moviesRepository.save(movieDoc);
            return convertToDto(updatedMovieDoc);
        } else {
            return null;
        }
    }

    public boolean deleteMovie(String id) {
        if (moviesRepository.existsById(id)) {
            moviesRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<MoviesDTO> getMoviesByGenre(String genre) {
        List<MovieDoc> movieDocs = moviesRepository.findAll().stream()
                .filter(movieDoc -> movieDoc.getGenres() != null &&
                        Arrays.stream(movieDoc.getGenres().split(","))
                                .map(String::trim)
                                .map(String::toLowerCase)
                                .anyMatch(genre.toLowerCase()::equals))
                .toList();
        return movieDocs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MoviesDTO> getTop10ByImdbRating() {
        Query query = new Query();
        query.addCriteria(Criteria.where("imdb.rating").ne(""));
        query.with(Sort.by(Sort.Order.desc("imdb.rating")));
        query.limit(10);
        List<MovieDoc> topMovieDocs = mongoTemplate.find(query, MovieDoc.class);
        return topMovieDocs.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
