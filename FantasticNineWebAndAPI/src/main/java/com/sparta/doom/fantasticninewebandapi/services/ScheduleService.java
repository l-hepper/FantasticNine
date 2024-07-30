package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.Movie;
import com.sparta.doom.fantasticninewebandapi.models.Schedule;
import com.sparta.doom.fantasticninewebandapi.models.Theater;
import com.sparta.doom.fantasticninewebandapi.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public List<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleByID(String Id){
        return scheduleRepository.findById(Id);
    }

    public List<Schedule> getSchedulesByTheatre(Theater theater) {
        String theatreId = theater.getId();
        return getSchedulesByTheatreId(theatreId);
    }

    private List<Schedule> getSchedulesByTheatreId(String theatreId) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getTheater().getId().equals(theatreId))
                .toList();
    }

    public List<Schedule> getSchedulesByMovie(Movie movie) {
        String movieId = movie.getId();
        return getSchedulesByMovieId(movieId);
    }

    private List<Schedule> getSchedulesByMovieId(String movieId) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getMovie().getId().equals(movieId))
                .toList();
    }

    private List<Schedule> getSchedulesByStartTimeAfter(LocalDateTime startTime) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getStartTime().isAfter(startTime))
                .toList();
    }

    private List<Schedule> getSchedulesByStartTimeBefore(LocalDateTime startTime) {
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.getStartTime().isBefore(startTime))
                .toList();
    }


    public Schedule addSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void removeSchedule(Schedule schedule) {
        scheduleRepository.delete(schedule);
    }

    public Schedule updateSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }





}
