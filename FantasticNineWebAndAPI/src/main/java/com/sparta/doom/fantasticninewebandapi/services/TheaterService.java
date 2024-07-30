package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
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

    public List<TheaterDoc> getAllTheaters() {
        return theaterRepository.findAll();
    }

    public TheaterDoc getTheaterByTheaterId(int theaterId) {
        Optional<TheaterDoc> theater = theaterRepository.findTheaterModelByTheaterId(theaterId);
        if (!theater.isPresent()) {
            throw new ResourceAccessException("Theater with id " + theaterId + " not found");
        }

        return theater.get();
    }

    public void deleteTheaterByTheaterId(int theaterId) {
        theaterRepository.deleteTheaterDocByTheaterId(theaterId);
        if (theaterRepository.findTheaterModelByTheaterId(theaterId).isPresent()) {
            throw new ResourceAccessException("Theater with id " + theaterId + " unable to delete");
        }
    }



}
