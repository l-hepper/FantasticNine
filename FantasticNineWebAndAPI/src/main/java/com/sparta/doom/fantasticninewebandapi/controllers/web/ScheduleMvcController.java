package com.sparta.doom.fantasticninewebandapi.controllers.web;

import com.sparta.doom.fantasticninewebandapi.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class ScheduleMvcController {
    // TODO Theatre has one schedule with many showings
    // TODO movies have many schedules with many showings at many theatres
    // TODO searches are based off of what called it
    private final ScheduleService ScheduleService;
    @Autowired
    public ScheduleMvcController(ScheduleService scheduleService) {
        ScheduleService = scheduleService;
    }
    @GetMapping("/theatre/{id}/schedules/")
    public String getScheduleForTheatre(@PathVariable String id, Model model) {
        model.addAttribute("schedules", ScheduleService.getSchedulesByTheatreId(id));
        model.addAttribute("searchType", "theatre");
        return "schedules";
    }

    @GetMapping("/movies/{id}/schedules/")
    public String getScheduleForMovie(@PathVariable String id, Model model) {
        model.addAttribute("schedules", ScheduleService.getSchedulesByMovieId(id));
        model.addAttribute("searchType", "movie");
        return "schedules";
    }

}