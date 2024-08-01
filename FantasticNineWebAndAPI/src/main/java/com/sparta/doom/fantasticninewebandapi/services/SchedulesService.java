package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.SchedulesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class SchedulesService {

    private final SchedulesRepository schedulesRepository;

    @Autowired
    public SchedulesService(SchedulesRepository schedulesRepository) {
        this.schedulesRepository = schedulesRepository;
    }

    public Stream<ScheduleDoc> getSchedules() {
        return schedulesRepository.findAllBy()
                .sorted(Comparator.comparing(ScheduleDoc::getStartTime));
    }

    public Optional<ScheduleDoc> getScheduleById(String Id){
        return schedulesRepository.findById(Id);
    }

    public Stream<ScheduleDoc> getSchedulesByTheatre(TheaterDoc theatre) {
        String theatreId = theatre.getId();
        return getSchedulesByTheaterId(theatreId);
    }

    public Stream<ScheduleDoc> getSchedulesByTheaterId(String theatreId) {
        return getSchedules()
                .filter(schedule -> schedule.getTheater().getId().equals(theatreId));
    }

    public Stream<ScheduleDoc> getSchedulesByMovie(MovieDoc movie) {
        String movieId = movie.getId();
        return getSchedulesByMovieId(movieId);
    }

    public Stream<ScheduleDoc> getSchedulesByMovieId(String movieId) {
        return getSchedules()
                .filter(schedule -> schedule.getMovie().getId().equals(movieId));
    }

    public Stream<ScheduleDoc> getSchedulesByStartTimeAfter(LocalDateTime startTime) {
        return getSchedules()
                .filter(schedule -> schedule.getStartTime().isAfter(startTime));
    }

    public Stream<ScheduleDoc> getSchedulesByStartTimeBefore(LocalDateTime startTime) {
        return getSchedules()
                .filter(schedule -> schedule.getStartTime().isBefore(startTime));
    }


    public Optional<ScheduleDoc> addSchedule(ScheduleDoc scheduleDoc) {
        return Optional.of(schedulesRepository.save(scheduleDoc));
    }

    public void removeSchedule(String id) {
        schedulesRepository.deleteById(id);
    }
    public void removeSchedule(ScheduleDoc scheduleDoc) {
        schedulesRepository.delete(scheduleDoc);
    }

    public Optional<ScheduleDoc> updateSchedule(ScheduleDoc scheduleDoc) {
        return Optional.of(schedulesRepository.save(scheduleDoc));
    }





}
