package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterModel;
import com.sparta.doom.fantasticninewebandapi.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;
import java.util.Optional;

@Service
public class TheaterService {

    private final TheaterRepository theaterRepository;

    @Autowired
    public TheaterService(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    public List<TheaterModel> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public TheaterModel getTheaterById(int theaterId) {
        Optional<TheaterModel> theater = theaterRepository.findTheaterModelByTheaterId(theaterId);
        if (!theater.isPresent()) {
            throw new ResourceAccessException("Theater with id " + theaterId + " not found");
        }

        return theater.get();
    }



}
