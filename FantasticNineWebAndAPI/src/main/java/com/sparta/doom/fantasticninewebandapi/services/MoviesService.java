package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.dtos.MoviesDTO;
import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
import com.sparta.doom.fantasticninewebandapi.repositories.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MoviesService {

    MoviesRepository moviesRepository;

    @Autowired
    public MoviesService(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public MoviesDTO convertToDto(MoviesModel movie) {
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

    private MoviesModel convertToEntity(MoviesDTO movieDto) {
        MoviesModel movie = new MoviesModel();
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
        List<MoviesModel> movies = moviesRepository.findAll();
        return movies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MoviesDTO getMovieById(String id) {
        Optional<MoviesModel> movie = moviesRepository.findById(id);
        return movie.map(this::convertToDto).orElse(null);
    }

    public Optional<MoviesModel> getMovieByTitle(String title) {
        return moviesRepository.findAll().stream()
                .filter(movie -> movie.getTitle().contains(title))
                .findFirst();
    }

    public MoviesDTO createMovie(MoviesDTO movieDto) {
        MoviesModel movie = convertToEntity(movieDto);
        MoviesModel savedMovie = moviesRepository.save(movie);
        return convertToDto(savedMovie);
    }

    public MoviesDTO updateMovie(String id, MoviesDTO movieDto) {
        if (moviesRepository.existsById(id)) {
            MoviesModel movie = convertToEntity(movieDto);
            movie.setId(id);
            MoviesModel updatedMovie = moviesRepository.save(movie);
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

}
