package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.Movie;
import com.sparta.doom.fantasticninewebandapi.models.Schedule;
import com.sparta.doom.fantasticninewebandapi.models.Theatre;
import com.sparta.doom.fantasticninewebandapi.repositories.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ScheduleRepoTest {


    //TODO : THESE ARE BAD TESTS, I WILL MAKE THEM BETTER
    @Autowired
    ScheduleRepository scheduleRepository;
    @Test
    void canAddSchedule(){

        Schedule schedule = new Schedule();
        Movie movie = new Movie();
        movie.setId("dddd");
        schedule.setMovie(movie);
        Theatre theatre = new Theatre();
        theatre.setId("rhguireghdrg");
        schedule.setTheatre(theatre);
        schedule.setStartTime(LocalDateTime.now());
        scheduleRepository.save(schedule);
        System.out.println(scheduleRepository.findAll());

    }

    @Test
    void canRemoveSchedule(){
        Schedule schedule = new Schedule();
        schedule.setId("66a8b0d4506f5a1a45ef7d64");
        scheduleRepository.delete(schedule);
        System.out.println(scheduleRepository.findAll());
    }

}
