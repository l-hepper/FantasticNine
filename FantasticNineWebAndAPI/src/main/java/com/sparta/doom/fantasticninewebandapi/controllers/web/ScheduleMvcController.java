package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.models.Schedule;
import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.ScheduleRepository;
import com.sparta.doom.fantasticninewebandapi.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping
public class ScheduleMvcController {
    // TODO Theatre has one schedule with many showings
    // TODO movies have many schedules with many showings at many theatres
    // TODO searches are based off of what called it
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;
    public final WebClient webClient;
    private String key;
    @Autowired
    public ScheduleMvcController(ScheduleService scheduleService, ScheduleRepository scheduleRepository, WebClient webClient) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
        this.webClient = webClient;
    }
    // Redirect gets to a single URL with two parameters
    // Redirect to /schedules/ or /schedules/{id}/{searchType}
    @GetMapping("/theaters/search/{id}/schedules/")
    public String getScheduleForTheatre(@PathVariable String id) {
        return "redirect:/schedules/theater/" +id+"/";
    }

    @GetMapping("/movies/search/{id}/schedules/")
    public String getScheduleForMovie(@PathVariable String id, Model model) {
        return "redirect:/schedules/movie/" +id+"/";
    }
    @GetMapping("/schedules/{searchType}/{id}/")
    public String getScheduleById(@PathVariable String searchType,@PathVariable String id, Model model) {
        if (Objects.equals(searchType, "theater")) {
            //schedules by theater id
            List<ScheduleDoc> schedules = webClient
                    .get()
                    .uri("api/schedules")
                    .header("DOOM-API-KEY", key)
                    .retrieve()
                    .bodyToFlux(ScheduleDoc.class)
                    .collectList()
                    .block();
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
                    .bodyToFlux(ScheduleDoc.class)
                    .collectList()
                    .block();
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
            List<ScheduleDoc> schedules = webClient.get()
                    .uri("api/schedules/"+id)
                    .header("DOOM-API-KEY", key)
                    .retrieve()
                    .bodyToFlux(ScheduleDoc.class)
                    .collectList()
                    .block();
            model.addAttribute("schedules",schedules);
        } else if (Objects.equals(searchType, "all")) {
            //all schedules
            List<ScheduleDoc> schedules = webClient
                    .get()
                    .uri("api/schedules")
                    .header("DOOM-API-KEY", key)
                    .retrieve()
                    .bodyToFlux(ScheduleDoc.class)
                    .collectList()
                    .block();
            model.addAttribute("schedules", schedules);
        }
        model.addAttribute("searchType", searchType);
        return "schedules";
    }
    @GetMapping("/schedules/")
    public String getAllSchedules(Model model) {
        model.addAttribute("schedules", scheduleRepository.findAll());
        model.addAttribute("searchType", "all");
        return "schedules";
    }

    @GetMapping("/schedules/create/")
    public String createSchedule() {
        return "schedules/schedules_create";
    }
    @PostMapping("/schedules/create/")
    public String createSchedulePost(@ModelAttribute ScheduleDoc schedule, Model model) {
        //scheduleService.addSchedule(schedule);
        return "redirect:/schedules/";
    }
}
