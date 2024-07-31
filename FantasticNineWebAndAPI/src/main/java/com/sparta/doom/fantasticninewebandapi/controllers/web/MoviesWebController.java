package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.dtos.MoviesDTO;
import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/movies/")
public class MoviesWebController {

    public final WebClient webClient;

    @Value("${key}")
    private String key;

    public MoviesWebController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping
    public String getMovies(Model model) {
        ResponseEntity<List<MoviesDTO>> moviesResponse = webClient
                .get()
                .uri("/api/movies/")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .toEntityList(MoviesDTO.class)
                .block();

        ArrayList<MoviesDTO> moviesList = new ArrayList<>();
        if (moviesResponse.hasBody()) {
            for (int i = 0; i<10; i++) {
                moviesList.add(moviesResponse.getBody().get(i));
            }
        }

        model.addAttribute("movies", moviesList);
        return "movies/movies";
    }

    @GetMapping("/search/")
    public String getSearchedMovies(@RequestParam String movieName, Model model) {
        List<MovieDoc> movies = webClient
                .get()
                .uri("api/movies/")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(MovieDoc.class)
                .collectList()
                .block();
        ArrayList<MovieDoc> returnMovies = new ArrayList<>();
        for (MovieDoc movie : movies) {
            if (movie.getTitle().toLowerCase().contains(movieName.toLowerCase())) {
                returnMovies.add(movie);
            }
        }
        model.addAttribute("movies", returnMovies);
        return "movies/movies";
    }

    @GetMapping("/search/{id}")
    public String getMovieDetails(@PathVariable String id, Model model) {
        MovieDoc movie = webClient
                .get()
                .uri("/api/movies/" + id)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToMono(MovieDoc.class)
                .block();
        model.addAttribute("movies", movie);
        return "movies/movies_details";
    }

    @GetMapping("/create/")
    public String createMovie() {
        return "movies/movies_create";
    }

    @PostMapping("/create/")
    public String createMoviePost(@ModelAttribute MovieDoc moviesModel, Model model) {
        webClient.post()
                .uri("/api/movies/create/")
                .header("DOOM-API-KEY", key)
                .bodyValue(moviesModel);
        model.addAttribute("movie", moviesModel);
        return "redirect:/movies/search/" + moviesModel.getId();
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable String id, @RequestBody MovieDoc moviesModel, Model model) {
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
