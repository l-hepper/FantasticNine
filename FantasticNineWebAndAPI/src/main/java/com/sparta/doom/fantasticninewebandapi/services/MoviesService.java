package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.dtos.MoviesDTO;
import com.sparta.doom.fantasticninewebandapi.models.Movie;
import com.sparta.doom.fantasticninewebandapi.repositories.MoviesRepository;
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

    public MoviesDTO convertToDto(Movie movie) {
        return new MoviesDTO(
                movie.getId(),
                movie.getAwards(),
                movie.getCast(),
                movie.getCountries(),
                movie.getDirectors(),
                movie.getFullplot(),
                movie.getGenres(),
                movie.getImdb(),
                movie.getLanguages(),
                movie.getLastupdated(),
                movie.getNum_mflix_comments(),
                movie.getPlot(),
                movie.getPoster(),
                movie.getRated(),
                movie.getReleased(),
                movie.getRuntime(),
                movie.getTitle(),
                movie.getTomatoes(),
                movie.getType(),
                movie.getWriters(),
                movie.getYear()
        );
    }

    private Movie convertToEntity(MoviesDTO movieDto) {
        Movie movie = new Movie();
        movie.setId(movieDto.getId());
        movie.setAwards(movieDto.getAwards());
        movie.setCast(movieDto.getCast());
        movie.setCountries(movieDto.getCountries());
        movie.setDirectors(movieDto.getDirectors());
        movie.setFullplot(movieDto.getFullplot());
        movie.setGenres(movieDto.getGenres());
        movie.setImdb(movieDto.getImdb());
        movie.setLanguages(movieDto.getLanguages());
        movie.setLastupdated(movieDto.getLastupdated());
        movie.setNum_mflix_comments(movieDto.getNumMflixComments());
        movie.setPlot(movieDto.getPlot());
        movie.setPoster(movieDto.getPoster());
        movie.setRated(movieDto.getRated());
        movie.setReleased(movieDto.getReleased());
        movie.setRuntime(movieDto.getRuntime());
        movie.setTitle(movieDto.getTitle());
        movie.setTomatoes(movieDto.getTomatoes());
        movie.setType(movieDto.getType());
        movie.setWriters(movieDto.getWriters());
        movie.setYear(movieDto.getYear());
        return movie;
    }

    public List<MoviesDTO> getAllMovies() {
        List<Movie> movies = moviesRepository.findAll();
        return movies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MoviesDTO getMovieById(String id) {
        Optional<Movie> movie = moviesRepository.findById(id);
        return movie.map(this::convertToDto).orElse(null);
    }

    public Optional<Movie> getMovieByTitle(String title) {
        return moviesRepository.findAll().stream()
                .filter(movie -> movie.getTitle().contains(title))
                .findFirst();
    }

    public List<Movie> getMoviesByPartialTitle(String title) {
        return moviesRepository.findAll().stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public MoviesDTO createMovie(MoviesDTO movieDto) {
        Movie movie = convertToEntity(movieDto);
        Movie savedMovie = moviesRepository.save(movie);
        return convertToDto(savedMovie);
    }

    public MoviesDTO updateMovie(String id, MoviesDTO movieDto) {
        if (moviesRepository.existsById(id)) {
            Movie movie = convertToEntity(movieDto);
            movie.setId(id);
            Movie updatedMovie = moviesRepository.save(movie);
            return convertToDto(updatedMovie);
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
        List<Movie> movies = moviesRepository.findAll().stream()
                .filter(movie -> movie.getGenres() != null &&
                        Arrays.stream(movie.getGenres().split(","))
                                .map(String::trim)
                                .map(String::toLowerCase)
                                .anyMatch(genre.toLowerCase()::equals))
                .toList();
        return movies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MoviesDTO> getTop10ByImdbRating() {
        Query query = new Query();
        query.addCriteria(Criteria.where("imdb.rating").ne(""));
        query.with(Sort.by(Sort.Order.desc("imdb.rating")));
        query.limit(10);
        List<Movie> topMovies = mongoTemplate.find(query, Movie.class);
        return topMovies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
