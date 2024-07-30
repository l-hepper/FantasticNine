package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.repositories.ScheduleRepository;
import com.sparta.doom.fantasticninewebandapi.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping
public class ScheduleMvcController {
    // TODO Theatre has one schedule with many showings
    // TODO movies have many schedules with many showings at many theatres
    // TODO searches are based off of what called it
    private final ScheduleService scheduleService;
    private ScheduleRepository scheduleRepository;
    @Autowired
    public ScheduleMvcController(ScheduleService scheduleService, ScheduleRepository scheduleRepository) {
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }
    @GetMapping("/theatre/search/{id}/schedules/")
    public String getScheduleForTheatre(@PathVariable String id, Model model) {
        model.addAttribute("schedules", scheduleService.getSchedulesByTheatreId(id));
        model.addAttribute("searchType", "theatre");
        return "schedules";
    }

    @GetMapping("/movies/search/{id}/schedules/")
    public String getScheduleForMovie(@PathVariable String id, Model model) {
        model.addAttribute("schedules", scheduleService.getSchedulesByMovieId(id));
        model.addAttribute("searchType", "movie");
        return "schedules";
    }
    @GetMapping("/schedules/")
    public String getAllSchedules(Model model) {
        model.addAttribute("schedules", scheduleRepository.findAll());
        model.addAttribute("searchType", "all");
        return "schedules";
    }

}
