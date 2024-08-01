package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.services.SchedulesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping
public class ScheduleWebController {
    // TODO Theatre has one schedule with many showings
    // TODO movies have many schedules with many showings at many theatres
    // TODO searches are based off of what called it
    // TODO Create
    // TODO Update
    // TODO Delete
    // TODO Web Page design
    public final WebClient webClient;
//    @Value("${key}")
    private String key;
    @Autowired
    public ScheduleWebController(SchedulesService schedulesService, WebClient webClient) {
        this.webClient = webClient;
    }
    // Redirect gets to a single URL with two parameters
    // Redirect to /schedules/ or /schedules/{id}/{searchType}
    @GetMapping("/theaters/search/{id}/schedules/")
    public String getSchedulesForTheatre(@PathVariable String id) {
        return "redirect:/schedules/theater/" +id+"/";
    }

    @GetMapping("/movies/search/{id}/schedules/")
    public String getSchedulesForMovie(@PathVariable String id, Model model) {
        return "redirect:/schedules/movie/" +id+"/";
    }
    @GetMapping("/schedules/{searchType}/{id}/")
    public String getSchedulesSearch(@PathVariable String searchType,@PathVariable String id, Model model) {
        if (Objects.equals(searchType, "theater")) {
            //schedules by theater id
            List<ScheduleDoc> schedules = webClient
                    .get()
                    .uri("api/schedules")
                    .header("DOOM-API-KEY", key)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<CollectionModel<EntityModel<ScheduleDoc>>>() {})
                    .block()
                    .getContent()
                    .stream()
                    .map(EntityModel::getContent)
                    .toList();
            ArrayList <ScheduleDoc> returnSchedules = new ArrayList<>();
            assert schedules != null;
            for (ScheduleDoc schedule : schedules) {
                if (schedule.getTheater().getId().equals(id)) {
                    returnSchedules.add(schedule);
                }
            }
            model.addAttribute("schedules", returnSchedules);
        } else if (Objects.equals(searchType, "movie")) {
            //schedules by movie id
            List<ScheduleDoc> schedules = webClient
                    .get()
                    .uri("api/schedules")
                    .header("DOOM-API-KEY", key)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<CollectionModel<EntityModel<ScheduleDoc>>>() {})
                    .block()
                    .getContent()
                    .stream()
                    .map(EntityModel::getContent)
                    .toList();
            ArrayList <ScheduleDoc> returnSchedules = new ArrayList<>();
            assert schedules != null;
            for (ScheduleDoc schedule : schedules) {
                if (schedule.getMovie().getId().equals(id)) {
                    returnSchedules.add(schedule);
                }
            }
            model.addAttribute("schedules", returnSchedules);
        } else if (Objects.equals(searchType, "schedule")) {
            //Schedules id
            List<ScheduleDoc> schedules = webClient
                    .get()
                    .uri("api/schedules/" + id)
                    .header("DOOM-API-KEY", key)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<CollectionModel<EntityModel<ScheduleDoc>>>() {})
                    .block()
                    .getContent()
                    .stream()
                    .map(EntityModel::getContent)
                    .toList();
            model.addAttribute("schedules",schedules);
        }
        model.addAttribute("searchType", searchType);
        return "schedules/show";
    }
    @GetMapping("/schedules/")
    public String getAllSchedules(Model model) {
        List<ScheduleDoc> schedules = webClient
                .get()
                .uri("api/schedules")
                .header("DOOM-API-KEY", key)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CollectionModel<EntityModel<ScheduleDoc>>>() {})
                .block()
                .getContent()
                .stream()
                .map(EntityModel::getContent)
                .toList();
        model.addAttribute("schedules", schedules);
        model.addAttribute("searchType", "all");
        return "schedules/show";
    }

    @GetMapping("/schedules/create/")
    public String createSchedule(Model model) {
        model.addAttribute("schedule", new ScheduleDoc());
        // add any other needed attributes
        return "schedules/create";
    }

    @PostMapping("/schedules/create/")
    public String createSchedule(@Valid @ModelAttribute ScheduleDoc schedule, Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Invalid schedule: " + errors);
        } else {
            webClient.post()
                    .uri("api/schedules/")
                    .header("DOOM-API-KEY", key)
                    .bodyValue(schedule);
            return "redirect:/schedules/schedule/" + schedule.getId() + "/";
        }
    }

    @PostMapping("/schedules/update/{id}/")
    public String updateSchedule(@PathVariable String id, @RequestBody ScheduleDoc schedule, Model model, Errors errors) {
        if (errors.hasErrors()) {
            throw new IllegalArgumentException("Invalid schedule: " + errors);
        } else {
            webClient.put()
                    .uri("api/schedules/" + schedule.getId())
                    .header("DOOM-API-KEY", key)
                    .bodyValue(schedule)
                    .retrieve()
                    .bodyToMono(ScheduleDoc.class)
                    .block();
            return "redirect:/schedules/schedule/" + schedule.getId() + "/";
        }
    }

    @GetMapping("/schedules/delete/{id}/")
    public String deleteSchedule(@PathVariable String id) {
        webClient
                .delete()
                .uri("api/schedules/" + id)
                .header("DOOM-API-KEY", key);
        return "redirect:/schedules/";
    }
}
