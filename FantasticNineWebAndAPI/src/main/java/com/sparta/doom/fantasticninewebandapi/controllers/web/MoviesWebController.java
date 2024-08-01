package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.services.MoviesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MoviesWebController {

    public final WebClient webClient;
    private final MoviesService moviesService;

    @Value("${key}")
    private String key;

    public MoviesWebController(WebClient webClient, MoviesService moviesService) {
        this.webClient = webClient;
        this.moviesService = moviesService;
    }

    @GetMapping
    public String getMovies(Model model) {
        return "redirect:/movies/pages";
    }

    @GetMapping("/pages")
    public String getMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "21") int size,
            Model model) {
        ResponseEntity<List<MovieDoc>> moviesResponse = webClient
                .get()
                .uri("/api/movies/pages?page=" + page + "&size=" + size)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .toEntityList(MovieDoc.class)
                .block();

        long totalPages = moviesService.getNumberOfMovies() / size;

        model.addAttribute("movies", moviesResponse.getBody());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);
        return "movies/movies";
    }

    @GetMapping("/search/{movieName}")
    public String getSearchedMovies(@PathVariable String movieName, Model model) {
        ResponseEntity<List<MovieDoc>> movies = webClient
                .get()
                .uri("/api/movies/search/" + movieName)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .toEntityList(MovieDoc.class)
                .block();
        ArrayList<MovieDoc> moviesList = new ArrayList<>();
        if (movies.hasBody()) {
            for (int i = 0; i<10; i++) {
                moviesList.add(movies.getBody().get(i));
            }
        }
        model.addAttribute("movies", moviesList);
        return "movies/movies";
    }

    @GetMapping("/{id}")
    public String getMovieDetails(@PathVariable String id, Model model) {
        MovieDoc movie = webClient
                .get()
                .uri("/api/movies/" + id)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToMono(MovieDoc.class)
                .block();
        model.addAttribute("movie", movie);
        return "movies/movies_details";
    }

    @GetMapping("/create")
    public String createMovie() {
        return "movies/movies_create";
    }

    @PostMapping("/create")
    public String createMoviePost(@ModelAttribute MovieDoc moviesModel, Model model) {
        webClient.post()
                .uri("/api/movies/create")
                .header("DOOM-API-KEY", key)
                .bodyValue(moviesModel);
        model.addAttribute("movie", moviesModel);
        return "redirect:/movies/search/" + moviesModel.getId();
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable String id, @RequestBody MovieDoc moviesModel, Model model) {
        webClient.patch()
                .uri("/api/movies/update" + id)
                .header("DOOM-API-KEY", key)
                .bodyValue(moviesModel);
        model.addAttribute("movie", moviesModel);
        return "redirect:/movies/search" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable String id) {
        webClient.delete()
                .uri("/api/movies/delete" + id)
                .header("DOOM-API-KEY", key);
        return "redirect:/movies";
    }

}
