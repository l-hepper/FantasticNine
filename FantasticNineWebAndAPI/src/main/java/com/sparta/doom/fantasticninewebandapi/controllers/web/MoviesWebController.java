package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.CommentDoc;
import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movies/")
public class MoviesWebController {

    public final WebClient webClient;

//    @Value("${key}") Causes bug
    private String key;

    public MoviesWebController(WebClient webClient) {
        this.webClient = webClient;
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

    @GetMapping
    public String getMovies(Model model) {
        List<MovieDoc> movies = webClient
                .get()
                .uri("/api/movies/")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(MovieDoc.class)
                .collectList()
                .block();
        model.addAttribute("movies", movies);
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

        List<CommentDoc> comments = fetchComments(movie.getId(),0);

//        List<CommentDoc> comments = webClient
//                .get()
//                .uri("/api/movies/"+id+"/comments")
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<CollectionModel<EntityModel<CommentDoc>>>() {})
//                .block().getContent().stream().map(EntityModel::getContent).toList();

        List<CommentDoc> returnComments = new ArrayList<>();

        for(CommentDoc comment : comments) {
            String emailAddress = comment.getEmail();
            UserDoc user = webClient.get().uri("/api/users/email/"+emailAddress)
                    .retrieve().bodyToMono(UserDoc.class).block();
            CommentDoc commentWithId = comment;
            commentWithId.setEmail(user.getId());
            returnComments.add(commentWithId);
        }

        model.addAttribute("movies", movie);
        model.addAttribute("comments", returnComments);
        return "movies/movies_details";
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

    private List<CommentDoc> fetchComments(String movieId, int page) {
        Mono<PagedModel<CommentDoc>> commentsMono = webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/api/movies/{movieId}/comments")
                        .queryParam("page", page)
                        .queryParam("size",10)
                        .build(movieId))
                .retrieve().bodyToMono(new ParameterizedTypeReference<PagedModel<CommentDoc>>() {});
        PagedModel<CommentDoc> comments = commentsMono.block();
        if (comments != null) {
            return new ArrayList<>(comments.getContent());
        }
        return new ArrayList<>();
    }
}
