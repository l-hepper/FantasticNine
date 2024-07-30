package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.MoviesModel;
import com.sparta.doom.fantasticninewebandapi.models.ScheduleDoc;
import com.sparta.doom.fantasticninewebandapi.models.TheaterModel;
import com.sparta.doom.fantasticninewebandapi.repositories.SchedulesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ScheduleDocRepoTest {


    //TODO : THESE ARE BAD TESTS, I WILL MAKE THEM BETTER
    @Autowired
    SchedulesRepository schedulesRepository;
    @Test
    void canAddSchedule(){
        long count = schedulesRepository.count();
        TheaterModel theatre = new TheaterModel();
        theatre.setId("59a47286cfa9a3a73e51e732");
        MoviesModel movie = new MoviesModel();
        movie.setId("573a1390f29313caabcd42e8");
        ScheduleDoc scheduleDoc = new ScheduleDoc();
        scheduleDoc.setTheatre(theatre);
        scheduleDoc.setMovie(movie);
        scheduleDoc.setStartTime(LocalDateTime.now());
        schedulesRepository.save(scheduleDoc);
        List<ScheduleDoc> scheduleDocs = schedulesRepository.findAll();
        Assertions.assertEquals(count +1, schedulesRepository.count());
    }

    @Test
    void canRemoveSchedule(){
        //TODO: Create a test for deleting it'd have to mock or something somehow
    }

}
