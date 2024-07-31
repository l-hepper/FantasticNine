package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
@RequestMapping("/theaters")
public class TheatersWebController {

    private final WebClient webClient;

    @Value("${key}")
    private String key;

    private static final int PAGE_SIZE = 10;

    public TheatersWebController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping
    public String getTheatres(
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        List<TheaterDoc> theaters = webClient
                .get()
                .uri("/api/theaters")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToFlux(TheaterDoc.class)
                .collectList()
                .block();

        int start = page * PAGE_SIZE;
        int end = Math.min(start + PAGE_SIZE, theaters.size());

        List<TheaterDoc> paginatedTheaters = theaters.subList(start, end);

        model.addAttribute("theaters", paginatedTheaters);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) theaters.size() / PAGE_SIZE));
        return "theaters/theaters";
    }

    @GetMapping("/{id}")
    public String getTheaterDetails(@PathVariable Integer id, Model model) {
        TheaterDoc theater = webClient
                .get()
                .uri("/api/theaters/" + id)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToMono(TheaterDoc.class)
                .block();
        model.addAttribute("theater", theater);
        return "theaters/theater_details";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("theater", new TheaterDoc());
        return "theaters/theater_create";
    }

    @PostMapping
    public String createTheater(@ModelAttribute TheaterDoc theater) {
        webClient
                .post()
                .uri("/api/theaters")
                .header("DOOM-API-KEY", key)
                .bodyValue(theater)
                .retrieve()
                .toBodilessEntity()
                .block();
        return "redirect:/theaters";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        TheaterDoc theater = webClient
                .get()
                .uri("/api/theaters/" + id)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToMono(TheaterDoc.class)
                .block();
        model.addAttribute("theater", theater);
        return "theaters/theater_update";
    }

    @PostMapping("/update")
    public String updateTheater(@ModelAttribute TheaterDoc theater) {
        webClient
                .put()
                .uri("/api/theaters")
                .header("DOOM-API-KEY", key)
                .bodyValue(theater)
                .retrieve()
                .bodyToMono(TheaterDoc.class)
                .block();
        return "redirect:/theaters";
    }

    @PostMapping("/delete/{id}")
    public String deleteTheater(@PathVariable Integer id) {
        webClient
                .delete()
                .uri("/api/theaters/" + id)
                .header("DOOM-API-KEY", key)
                .retrieve()
                .toBodilessEntity()
                .block();
        return "redirect:/theaters";
    }
}
