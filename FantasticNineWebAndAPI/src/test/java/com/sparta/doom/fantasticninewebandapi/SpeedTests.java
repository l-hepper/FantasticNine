package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.MoviesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class SpeedTests {

    @Autowired
    private MoviesRepository moviesRepository;

    @Test
    public void testMethodMovieListsExecutionTime() {
        // Get the start time
        long startTime = System.currentTimeMillis();

        // Call the method you want to time
        List<MovieDoc> movies = moviesRepository.findAll();

        // Get the end time
        long endTime = System.currentTimeMillis();

        // Calculate the time taken
        long timeTaken = endTime - startTime;

        // Print the time taken to the console
        System.out.println("Time taken for list of movies: " + timeTaken + " milliseconds");

        Assertions.assertFalse(movies.isEmpty());
    }

    @Test
    void testMovieStreamExecutionTime() {
        // Get the start time
        long startTime = System.currentTimeMillis();

        // Call the method you want to time
        Stream<MovieDoc> movies = moviesRepository.findAllBy();

        // Get the end time
        long endTime = System.currentTimeMillis();

        // Calculate the time taken
        long timeTaken = endTime - startTime;

        // Print the time taken to the console
        System.out.println("Time taken for stream of movies: " + timeTaken + " milliseconds");

        Assertions.assertFalse(movies.toList().isEmpty());
    }

    @Test
    void testMovieStreamIsSizeOfMoviesList() {
        Stream<MovieDoc> movies = moviesRepository.findAllBy();
        List<MovieDoc> listMovies = moviesRepository.findAll();

        Assertions.assertEquals(movies.toList().size(), listMovies.size());
    }

}
