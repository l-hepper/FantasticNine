package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterModel;
import com.sparta.doom.fantasticninewebandapi.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TheaterAPIController {

    private final TheaterService theaterService;

    @Autowired
    public TheaterAPIController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping("/theaters")
    public ResponseEntity<List<TheaterModel>> getTheaters() {
        List<TheaterModel> theaters = theaterService.getAllTheaters();
        return ResponseEntity.ok(theaters);
    }

    @GetMapping("theaters/{id}")
    public ResponseEntity<TheaterModel> getTheaterById(@PathVariable int id) {
        TheaterModel foundTheater = theaterService.getTheaterById(id);
        return ResponseEntity.ok(foundTheater);
    }


}
