package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.Movie;
import com.sparta.doom.fantasticninewebandapi.models.Schedule;
import com.sparta.doom.fantasticninewebandapi.models.Theater;
import com.sparta.doom.fantasticninewebandapi.repositories.ScheduleRepository;
import org.junit.jupiter.api.Assertions;
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
        long count = scheduleRepository.count();
        Theater theater = new Theater("59a47286cfa9a3a73e51e732");
        Movie movie = new Movie("573a1390f29313caabcd42e8");
        Schedule schedule = new Schedule();
        schedule.setTheater(theater);
        schedule.setMovie(movie);
        schedule.setStartTime(LocalDateTime.now());
        scheduleRepository.save(schedule);
        Assertions.assertEquals(count +1, scheduleRepository.count());
    }

    @Test
    void canRemoveSchedule(){
        //TODO: Create a test for deleting it'd have to mock or something somehow
    }

}
