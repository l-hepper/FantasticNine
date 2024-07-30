package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.dtos.MoviesDTO;
import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
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
@RequestMapping("/movies/")
public class MoviesWebController {

    public final WebClient webClient;

    @Value("${key}")
    private String key;

    public MoviesWebController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/create/")
    public String createMovie() {
        return "movies/movies_create";
    }

    @PostMapping("/create/")
    public String createMoviePost(@ModelAttribute MoviesModel moviesModel, Model model) {
        webClient.post()
                .uri("/api/movies/create/")
                .header("DOOM-API-KEY", key)
                .bodyValue(moviesModel);
        model.addAttribute("movie", moviesModel);
        return "redirect:/movies/search/" + moviesModel.getId();
    }

    @GetMapping
    public String getMovies(Model model) {
        List<MoviesDTO> movies = webClient
                .get()
                .uri("/api/movies/")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(MoviesDTO.class)
                .collectList()
                .block();
        model.addAttribute("movies", movies);
        return "movies/movies";
    }

    @GetMapping("/search/")
    public String getSearchedMovies(@RequestParam String movieName, Model model) {
        List<MoviesModel> movies = webClient
                .get()
                .uri("api/movies/")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(MoviesModel.class)
                .collectList()
                .block();
        ArrayList<MoviesModel> returnMovies = new ArrayList<>();
        for (MoviesModel movie : movies) {
            if (movie.getTitle().toLowerCase().contains(movieName.toLowerCase())) {
                returnMovies.add(movie);
            }
        }
        model.addAttribute("movies", returnMovies);
        return "movies/movies";
    }

    @GetMapping("/search/{id}")
    public String getMovieDetails(@PathVariable String id, Model model) {
        MoviesModel movie = webClient
                .get()
                .uri("/api/movies/" + id)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToMono(MoviesModel.class)
                .block();
        model.addAttribute("movies", movie);
        return "movies/movies_details";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable String id, @RequestBody MoviesModel moviesModel, Model model) {
        webClient.patch()
                .uri("/api/movies/update/" + id)
                .header("DOOM-API-KEY", key)
                .bodyValue(moviesModel);
        model.addAttribute("movie", moviesModel);
        return "redirect:/movies/search/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable String id) {
        webClient.delete()
                .uri("/api/movies/delete/" + id)
                .header("DOOM-API-KEY", key);
        return "redirect:/movies/";
    }

}
