package com.sparta.doom.fantasticninewebandapi.controllers;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class TheaterController {

    private final TheaterRepository;

    public TheaterController(TheaterRepository theaterRepository) {

    }

}
