package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.models.TheaterModel;
import com.sparta.doom.fantasticninewebandapi.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TheatersApiController {

    private final TheaterRepository theaterRepository;

    @Autowired
    public TheatersApiController(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @GetMapping("/theaters")
    public ResponseEntity<List<TheaterModel>> getTheaters() {
        List<TheaterModel> theaters = theaterRepository.findAll();
        return ResponseEntity.ok(theaters);
    }


}
