package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TheatresApiController {

    private final TheaterRepository theatersRepository;

    @Autowired
    public TheatresApiController(TheaterRepository theatersRepository) {
        this.theatersRepository = theatersRepository;
    }

    @GetMapping("/theaters")
    public ResponseEntity<List<TheaterDoc>> getTheaters() {
        List<TheaterDoc> theaters = theatersRepository.findAll();
        return ResponseEntity.ok(theaters);
    }


}
