package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GeneralWebController {

    private final WebClient webClient;
    private final String API_KEY = "unique-api-key-123";
    private String key;
    private static final int BATCH_SIZE = 5;


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
                .uri("/api/movies/top-rated")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(MovieDoc.class)
                .collectList()
                .block();
        assert topRatedMovies != null;
        List<List<MovieDoc>> batchedMovies = batchMovies(topRatedMovies, BATCH_SIZE);
        model.addAttribute("topRatedMovies", batchedMovies);
        return "home";
    }

    private List<List<MovieDoc>> batchMovies(List<MovieDoc> movies, int batchSize) {
        List<List<MovieDoc>> batches = new ArrayList<>();
        for (int i=0; i < movies.size(); i+= batchSize) {
            batches.add(new ArrayList<>(movies.subList(i, Math.min(i+batchSize, movies.size()))));
        }
        return batches;
    }



}
