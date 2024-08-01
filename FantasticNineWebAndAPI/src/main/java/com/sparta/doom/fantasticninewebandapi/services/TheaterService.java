package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.exceptions.ResourceNotFoundException;
import com.sparta.doom.fantasticninewebandapi.exceptions.UnableToDeleteException;
import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
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
        if (theater.isEmpty()) {
            throw new ResourceNotFoundException("Theater with id " + theaterId + " not found");
        }

        return theater.get();
    }

    public List<TheaterDoc> getTheatersByCityName(String cityName) {
        List<TheaterDoc> theaters = theaterRepository.getTheaterDocsByLocation_Address_City(cityName);
        return theaters;
    }

    public void deleteTheaterByTheaterId(int theaterId) {
        if (!theaterRepository.findTheaterModelByTheaterId(theaterId).isPresent()) {
            throw new ResourceNotFoundException("Theater with id " + theaterId + " not found");
        }

        theaterRepository.deleteTheaterDocByTheaterId(theaterId);
        if (theaterRepository.findTheaterModelByTheaterId(theaterId).isPresent()) {
            throw new UnableToDeleteException("Theater with id " + theaterId + " unable to delete");
        }
    }

    public void createTheater(TheaterDoc theaterDoc) {
        System.out.println(theaterDoc.getTheaterId());
        theaterRepository.save(theaterDoc);
    }

    public TheaterDoc updateTheater(TheaterDoc theaterDoc) {
        Optional<TheaterDoc> foundTheater = theaterRepository.findTheaterModelByTheaterId(theaterDoc.getTheaterId());
        if (!foundTheater.isPresent()) {
            throw new ResourceNotFoundException("Theater with id " + theaterDoc.getId() + " does not exist. Unable to update.");
        }
        TheaterDoc toReturn = theaterRepository.save(theaterDoc);
        return toReturn;
    }



}
