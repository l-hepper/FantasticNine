package com.sparta.doom.fantasticninewebandapi.controllers.api;

import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.services.SchedulesService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/schedules")
public class SchedulesApiController {

    private final SchedulesService schedulesService;

    public SchedulesApiController(SchedulesService schedulesService) {
        this.schedulesService = schedulesService;
    }

    @GetMapping
    public CollectionModel<EntityModel<ScheduleDoc>> getSchedules() {
        List<EntityModel<ScheduleDoc>> scheduleDocs = schedulesService.getSchedules().stream()
                .map(this::mapScheduleHateoas)
                .toList();
        if(scheduleDocs.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return CollectionModel.of(scheduleDocs)
                .add(linkTo(methodOn(SchedulesApiController.class).getSchedules()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ScheduleDoc>> getScheduleById(@PathVariable String id) {
        return ResponseEntity.ok(schedulesService.getScheduleById(id).map(this::mapScheduleHateoas).orElseThrow(NoSuchElementException::new));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ScheduleDoc>> addSchedule(@RequestBody ScheduleDoc newSchedule) {
        if(newSchedule == null ||newSchedule.getMovie() == null || newSchedule.getTheater() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        EntityModel<ScheduleDoc> newScheduleModel = mapScheduleHateoas(schedulesService.addSchedule(newSchedule));
        return ResponseEntity.ok().body(newScheduleModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id) {
        schedulesService.removeSchedule(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ScheduleDoc>> updateSchedule(@PathVariable String id, @RequestBody ScheduleDoc schedule) {
        if(schedule == null || schedule.getMovie() == null || schedule.getTheater() == null || !id.equals(schedule.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        EntityModel<ScheduleDoc> updatedSchedule = mapScheduleHateoas(schedulesService.updateSchedule(schedule));
        return ResponseEntity.ok().body(updatedSchedule);
    }

    private EntityModel<ScheduleDoc> mapScheduleHateoas(ScheduleDoc doc) {
        Link selfLink = linkTo(methodOn(SchedulesApiController.class)
                .getScheduleById(doc.getId()))
                .withSelfRel();
        Link movieLink = doc.getMovie() == null ? Link.of("Not Found") : linkTo(methodOn(MoviesApiController.class)
                .getMovieById(doc.getMovie().getId()))
                .withRel("movie");
        Link theaterLink = doc.getTheater() == null ? Link.of("Not Found") : linkTo(methodOn(TheaterApiController.class)
                .getTheaters())
                .slash(doc.getTheater().getId())
                .withRel("theater");
        return EntityModel.of(doc, selfLink, movieLink, theaterLink);
    }


}
