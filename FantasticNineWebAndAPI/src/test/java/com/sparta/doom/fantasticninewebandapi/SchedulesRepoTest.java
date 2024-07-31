package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import com.sparta.doom.fantasticninewebandapi.models.theater.repositories.SchedulesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class SchedulesRepoTest {


    //TODO : THESE ARE BAD TESTS, I WILL MAKE THEM BETTER
    @Autowired
    SchedulesRepository schedulesRepository;
    @Test
    void canAddSchedule(){
        long count = schedulesRepository.count();
        TheaterDoc theatre = new TheaterDoc();
        theatre.setId("59a47286cfa9a3a73e51e732");
        MovieDoc movie = new MovieDoc();
        movie.setId("573a1390f29313caabcd42e8");
        ScheduleDoc scheduleDoc = new ScheduleDoc();
        scheduleDoc.setTheater(theatre);
        scheduleDoc.setMovie(movie);
        scheduleDoc.setStartTime(LocalDateTime.now());
        schedulesRepository.save(scheduleDoc);
        Assertions.assertEquals(count +1, schedulesRepository.count());
    }

    @Test
    void peekAtSchedules(){
        List<ScheduleDoc> scheduleDocs = schedulesRepository.findAll();
        Assertions.assertNotNull(scheduleDocs);
    }

    @Test
    void canRemoveSchedule(){
        //TODO: Create a test for deleting it'd have to mock or something somehow
    }

}
