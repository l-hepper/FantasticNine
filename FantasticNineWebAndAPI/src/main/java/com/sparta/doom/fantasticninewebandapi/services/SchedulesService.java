package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import com.sparta.doom.fantasticninewebandapi.models.theater.repositories.SchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SchedulesService {

    private SchedulesRepository schedulesRepository;

    @Autowired
    public SchedulesService(SchedulesRepository schedulesRepository) {
        this.schedulesRepository = schedulesRepository;
    }

    public List<ScheduleDoc> getSchedules() {
        return schedulesRepository.findAll().stream()
                .sorted(Comparator.comparing(ScheduleDoc::getStartTime))
                .toList();
    }

    public Optional<ScheduleDoc> getScheduleById(String Id){
        return schedulesRepository.findById(Id);
    }

    public List<ScheduleDoc> getSchedulesByTheatre(TheaterDoc theatre) {
        String theatreId = theatre.getId();
        return getSchedulesByTheatreId(theatreId);
    }

    public List<ScheduleDoc> getSchedulesByTheatreId(String theatreId) {
        return getSchedules().stream()
                .filter(schedule -> schedule.getTheater().getId().equals(theatreId))
                .toList();
    }

    public List<ScheduleDoc> getSchedulesByMovie(MovieDoc movie) {
        String movieId = movie.getId();
        return getSchedulesByMovieId(movieId);
    }

    public List<ScheduleDoc> getSchedulesByMovieId(String movieId) {
        return getSchedules().stream()
                .filter(schedule -> schedule.getMovie().getId().equals(movieId))
                .toList();
    }

    public List<ScheduleDoc> getSchedulesByStartTimeAfter(LocalDateTime startTime) {
        return getSchedules().stream()
                .filter(schedule -> schedule.getStartTime().isAfter(startTime))
                .toList();
    }

    public List<ScheduleDoc> getSchedulesByStartTimeBefore(LocalDateTime startTime) {
        return getSchedules().stream()
                .filter(schedule -> schedule.getStartTime().isBefore(startTime))
                .toList();
    }


    public ScheduleDoc addSchedule(ScheduleDoc scheduleDoc) {
        return schedulesRepository.save(scheduleDoc);
    }

    public void removeSchedule(String id) {
        schedulesRepository.deleteById(id);
    }
    public void removeSchedule(ScheduleDoc scheduleDoc) {
        schedulesRepository.delete(scheduleDoc);
    }

    public ScheduleDoc updateSchedule(ScheduleDoc scheduleDoc) {
        return schedulesRepository.save(scheduleDoc);
    }





}
