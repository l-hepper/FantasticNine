package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.controllers.TheaterAPIController;
import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterModel;
import com.sparta.doom.fantasticninewebandapi.repositories.TheaterRepository;
import com.sparta.doom.fantasticninewebandapi.services.TheaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TheaterAPIControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TheaterAPIController theaterController;

    @MockBean
    private TheaterService theaterService;

    @MockBean
    private TheaterRepository theaterRepository;

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testGetTheaters() throws Exception {
        List<TheaterModel> theaters = theaterRepository.findAll();
        Mockito.when(theaterService.getAllTheaters()).thenReturn(theaters);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/theaters"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
