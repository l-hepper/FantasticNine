package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class GeneralWebController {

    private final WebClient webClient;
    private final String API_KEY = "unique-api-key-123";
    private String key;
    private static final int MOVIE_SIZE = 5;
    private static final int GENRE_SIZE = 5;

    private static final List<String> ALL_GENRES = Arrays.asList(
            "short", "western", "drama", "animation", "comedy", "crime",
            "history", "action", "biography", "family", "romance", "fantasy",
            "mystery", "war", "adventure", "thriller", "documentary", "musical",
            "music", "film-noir", "sport", "horror", "sci-fi", "talk-show", "news"
    );


    public GeneralWebController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/")
    public RedirectView landingPage() {
        return new RedirectView("/home");
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        List<MovieDoc> topRatedMovies = webClient
                .get()
                .uri("/api/movies/top-rated-movies")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(MovieDoc.class)
                .collectList()
                .block();
        assert topRatedMovies != null;
        List<List<MovieDoc>> batchedMovies = batchMovies(topRatedMovies);

        List<MovieDoc> topRatedSeries = webClient
                .get()
                .uri("/api/series/top-rated")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(MovieDoc.class)
                .collectList()
                .block();
        assert topRatedSeries != null;
        List<List<MovieDoc>> batchedSeries = batchMovies(topRatedSeries);

        List<List<String>> batchedGenres = batchGenres();
        model.addAttribute("topRatedMovies", batchedMovies);
        model.addAttribute("topRatedSeries", batchedSeries);
        model.addAttribute("allGenres", batchedGenres);
        return "home";
    }

    private List<List<MovieDoc>> batchMovies(List<MovieDoc> movies) {
        List<List<MovieDoc>> batches = new ArrayList<>();
        for (int i=0; i < movies.size(); i+= GeneralWebController.MOVIE_SIZE) {
            batches.add(new ArrayList<>(movies.subList(i, Math.min(i+ GeneralWebController.MOVIE_SIZE, movies.size()))));
        }
        return batches;
    }

    private List<List<String>> batchGenres() {
        return IntStream.range(0, (GeneralWebController.ALL_GENRES.size() + GENRE_SIZE - 1) / GENRE_SIZE)
                .mapToObj(i -> GeneralWebController.ALL_GENRES.subList(i * GENRE_SIZE, Math.min(GeneralWebController.ALL_GENRES.size(), (i + 1) * GENRE_SIZE)))
                .collect(Collectors.toList());
    }





}
