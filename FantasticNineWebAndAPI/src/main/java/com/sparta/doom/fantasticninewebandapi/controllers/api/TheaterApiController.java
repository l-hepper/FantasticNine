package com.sparta.doom.fantasticninewebandapi.controllers.api;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import com.sparta.doom.fantasticninewebandapi.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TheaterApiController {

    private final TheaterService theaterService;

    @Autowired
    public TheaterApiController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping("/theaters")
    public ResponseEntity<List<TheaterDoc>> getTheaters() {
        List<TheaterDoc> theaters = theaterService.getAllTheaters();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(theaters);
    }

    @GetMapping("theaters/{id}")
    public ResponseEntity<TheaterDoc> getTheaterById(@PathVariable int id) {
        TheaterDoc theater = theaterService.getTheaterByTheaterId(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(theater);
    }

    @DeleteMapping("theaters/{id}")
    public ResponseEntity<HttpStatus> deleteTheater(int id) {
        theaterService.deleteTheaterByTheaterId(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
