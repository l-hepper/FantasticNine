package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
import com.sparta.doom.fantasticninewebandapi.models.TheaterModel;
import com.sparta.doom.fantasticninewebandapi.repositories.MoviesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MoviesController {

    private MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    @GetMapping
    public ResponseEntity<List<MoviesModel>> getTheaters() {
        List<MoviesModel> movies = moviesRepository.findAll();
        return ResponseEntity.ok(movies);
    }

}
