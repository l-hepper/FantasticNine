package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.SchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return schedulesRepository.findAll();
    }

    public Optional<ScheduleDoc> getScheduleByID(String Id){
        return schedulesRepository.findById(Id);
    }

    public List<ScheduleDoc> getSchedulesByTheatre(Theatre theatre) {
        String theatreId = theatre.getId();
        return getSchedulesByTheatreId(theatreId);
    }

    private List<ScheduleDoc> getSchedulesByTheatreId(String theatreId) {
        return schedulesRepository.findAll().stream()
                .filter(schedule -> schedule.getTheatre().getId().equals(theatreId))
                .toList();
    }

    public List<ScheduleDoc> getSchedulesByMovie(Movie movie) {
        String movieId = movie.getId();
        return getSchedulesByMovieId(movieId);
    }

    private List<ScheduleDoc> getSchedulesByMovieId(String movieId) {
        return schedulesRepository.findAll().stream()
                .filter(schedule -> schedule.getMovie().getId().equals(movieId))
                .toList();
    }

    private List<ScheduleDoc> getSchedulesByStartTimeAfter(LocalDateTime startTime) {
        return schedulesRepository.findAll().stream()
                .filter(schedule -> schedule.getStartTime().isAfter(startTime))
                .toList();
    }

    private List<ScheduleDoc> getSchedulesByStartTimeBefore(LocalDateTime startTime) {
        return schedulesRepository.findAll().stream()
                .filter(schedule -> schedule.getStartTime().isBefore(startTime))
                .toList();
    }


    public ScheduleDoc addSchedule(ScheduleDoc scheduleDoc) {
        return schedulesRepository.save(scheduleDoc);
    }

    public void removeSchedule(ScheduleDoc scheduleDoc) {
        schedulesRepository.delete(scheduleDoc);
    }

    public ScheduleDoc updateSchedule(ScheduleDoc scheduleDoc) {
        return schedulesRepository.save(scheduleDoc);
    }





}
