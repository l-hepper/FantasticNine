package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.models.TheaterModel;
import com.sparta.doom.fantasticninewebandapi.repositories.TheaterRepository;
import com.sparta.doom.fantasticninewebandapi.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TheaterController {

    private final TheaterService theaterService;

    @Autowired
    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping("/theaters")
    public ResponseEntity<List<TheaterModel>> getTheaters() {
        List<TheaterModel> theaters = theaterService.getAllTheaters();
        return ResponseEntity.ok(theaters);
    }


}
