package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.MovieDoc;
import com.sparta.doom.fantasticninewebandapi.models.Schedule;
import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.ScheduleRepository;
import com.sparta.doom.fantasticninewebandapi.services.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ScheduleServiceTest {

    private static ScheduleRepository scheduleRepository;

    private static ScheduleService scheduleService;

    Schedule schedule1 = new Schedule("1", new TheaterDoc(), new MovieDoc(), LocalDateTime.now().plusDays(1));
    Schedule schedule2 = new Schedule("2", new TheaterDoc(), new MovieDoc(), LocalDateTime.now().plusDays(2));


    @BeforeAll
    public static void setUp() {
        scheduleRepository = Mockito.mock(ScheduleRepository.class);
        scheduleService = new ScheduleService(scheduleRepository);
    }

    @Test
    public void testGetSchedules() {
        when(scheduleRepository.findAll()).thenReturn(Arrays.asList(schedule1, schedule2));

        List<Schedule> schedules = scheduleService.getSchedules();

        assertNotNull(schedules);
        Assertions.assertEquals(2, schedules.size());
    }

    @Test
    public void testGetScheduleById() {
        when(scheduleRepository.findById("1")).thenReturn(Optional.of(schedule1));

        Optional<Schedule> retrievedSchedule = scheduleService.getScheduleByID("1");

        assertTrue(retrievedSchedule.isPresent());
        assertEquals("1", retrievedSchedule.get().getId());
        verify(scheduleRepository, times(1)).findById("1");
    }

    @Test
    public void testAddSchedule() {
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule1);

        Schedule savedSchedule = scheduleService.addSchedule(schedule1);

        assertNotNull(savedSchedule);
        assertEquals("1", savedSchedule.getId());
        verify(scheduleRepository, times(1)).save(schedule1);
    }

    @Test
    public void testRemoveSchedule() {
        doNothing().when(scheduleRepository).delete(schedule1);

        scheduleService.removeSchedule(schedule1);

        verify(scheduleRepository, times(1)).delete(schedule1);
    }

    @Test
    public void testUpdateSchedule() {
        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule1);

        Schedule updatedSchedule = scheduleService.updateSchedule(schedule1);

        assertNotNull(updatedSchedule);
        assertEquals("1", updatedSchedule.getId());
        verify(scheduleRepository, times(1)).save(schedule1);
    }

    @Test
    public void testGetSchedulesByTheatre() {
        TheaterDoc theatre = new TheaterDoc();
        when(scheduleRepository.findAll()).thenReturn(Arrays.asList(schedule1));

        List<Schedule> schedules = scheduleService.getSchedulesByTheatre(theatre);

        assertNotNull(schedules);
        assertEquals(1, schedules.size());
        assertEquals("1", schedules.get(0).getTheatre().getId());
    }

    @Test
    public void testGetSchedulesByMovie() {
        MovieDoc movie = new MovieDoc();
        when(scheduleRepository.findAll()).thenReturn(Arrays.asList(schedule1));

        List<Schedule> schedules = scheduleService.getSchedulesByMovie(movie);

        assertNotNull(schedules);
        assertEquals(1, schedules.size());
        assertEquals("1", schedules.get(0).getMovie().getId());
    }
}
