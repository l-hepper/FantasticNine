package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.view.RedirectView;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GeneralWebController {

    private final WebClient webClient;
    @Value("${jwt.auth}")
    private String AUTH_HEADER;

    private final SecretKey secretKey;

    private final String API_KEY = "unique-api-key-123";
    private String key;
    private static final int BATCH_SIZE = 5;

    @Autowired
    public GeneralWebController(WebClient webClient, SecretKey secretKey) {
        this.secretKey = secretKey;
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
                .header(AUTH_HEADER,"Bearer " + secretKey.toString())
                .retrieve()
                .bodyToFlux(MovieDoc.class)
                .collectList()
                .block();
        assert topRatedMovies != null;
        List<List<MovieDoc>> batchedMovies = batchMovies(topRatedMovies);
        model.addAttribute("topRatedMovies", batchedMovies);
        return "home";
    }

    private List<List<MovieDoc>> batchMovies(List<MovieDoc> movies) {
        List<List<MovieDoc>> batches = new ArrayList<>();
        for (int i=0; i < movies.size(); i+= GeneralWebController.BATCH_SIZE) {
            batches.add(new ArrayList<>(movies.subList(i, Math.min(i+ GeneralWebController.BATCH_SIZE, movies.size()))));
        }
        return batches;
    }



}
