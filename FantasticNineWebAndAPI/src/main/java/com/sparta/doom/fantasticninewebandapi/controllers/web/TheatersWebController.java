package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
@RequestMapping("/theatres")
public class TheatersWebController {

    private final WebClient webClient;

    @Value("${key}")
    private String key;

    public TheatersWebController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping
    public String getTheatres(Model model) {
        List<TheaterModel> theatres = webClient
                .get()
                .uri("/api/theaters")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(TheaterModel.class)
                .collectList()
                .block();
        model.addAttribute("theatres", theatres);
        return "theaters";
    }

    @GetMapping("/search/{id}")
    public String getTheaterDetails(@PathVariable String id, Model model) {
        TheaterModel theater = webClient
                .get()
                .uri("/api/theaters/" + id)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToMono(TheaterModel.class)
                .block();
        model.addAttribute("theater", theater);
        return "theater_details";
    }

}
