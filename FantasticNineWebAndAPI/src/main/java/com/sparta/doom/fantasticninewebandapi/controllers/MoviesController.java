package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.dtos.MoviesDTO;
import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
import com.sparta.doom.fantasticninewebandapi.services.MoviesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/movies/")
public class MoviesController {

    private final MoviesService moviesService;

    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @GetMapping
    public ResponseEntity<List<MoviesDTO>> getAllMovies() {
        List<MoviesDTO> movies = moviesService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoviesDTO> getMovieById(@PathVariable String id) {
        MoviesDTO movie = moviesService.getMovieById(id);
        return movie != null ? ResponseEntity.ok(movie) : ResponseEntity.notFound().build();
    }

    //TODO add regex
    @GetMapping("/title/{title}")
    public ResponseEntity<MoviesDTO> getMovieByTitle(@PathVariable String title) {
        Optional<MoviesModel> movie = moviesService.getMovieByTitle(title);
        if (movie.isPresent()) {
            MoviesDTO movieDto = moviesService.convertToDto(movie.get());
            return ResponseEntity.ok(movieDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<MoviesDTO> createMovie(@RequestBody MoviesDTO movieDto, @RequestHeader(name = "DOOM-API-KEY") String key) {
        MoviesDTO createdMovie = moviesService.createMovie(movieDto);
        return ResponseEntity.status(201).body(createdMovie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoviesDTO> updateMovie(@PathVariable String id, @RequestBody MoviesDTO movieDto) {
        MoviesDTO updatedMovie = moviesService.updateMovie(id, movieDto);
        return updatedMovie != null ? ResponseEntity.ok(updatedMovie) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String id) {
        boolean isDeleted = moviesService.deleteMovie(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<MoviesDTO>> getMoviesByGenre(@PathVariable String genre) {
        List<MoviesDTO> movies = moviesService.getMoviesByGenre(genre);
        return ResponseEntity.ok(movies);
    }
}
