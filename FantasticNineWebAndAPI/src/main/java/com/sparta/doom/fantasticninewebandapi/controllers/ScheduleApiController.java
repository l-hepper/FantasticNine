package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.services.SchedulesService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleApiController {

    private final SchedulesService schedulesService;

    public ScheduleApiController(SchedulesService schedulesService) {
        this.schedulesService = schedulesService;
    }

    @GetMapping
    public CollectionModel<ScheduleDoc> getSchedules() {
        List<ScheduleDoc> scheduleDocs = schedulesService.getSchedules();
        return CollectionModel.of(scheduleDocs);
    }
}
