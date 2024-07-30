package com.sparta.doom.fantasticninewebandapi;

import com.sparta.doom.fantasticninewebandapi.models.theater.Address;
import com.sparta.doom.fantasticninewebandapi.models.theater.Geo;
import com.sparta.doom.fantasticninewebandapi.models.theater.Location;
import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterDoc;
import com.sparta.doom.fantasticninewebandapi.models.theater.TheaterModel;
import com.sparta.doom.fantasticninewebandapi.repositories.TheaterRepository;
import com.sparta.doom.fantasticninewebandapi.services.TheaterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TheaterAPIControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TheaterService theaterService;

    @MockBean
    private TheaterRepository theaterRepository;

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testGetTheaters() throws Exception {
        List<TheaterDoc> theaters = theaterRepository.findAll();
        Mockito.when(theaterService.getAllTheaters()).thenReturn(theaters);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/theaters"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "testUser", roles = {"USER"})
    public void testGetTheaterById() throws Exception {
        TheaterDoc theater = new TheaterDoc();
        theater.setId("59a47286cfa9a3a73e51e72c");
        theater.setTheaterId(1000);
        Location location = new Location();
        Address address = new Address();
        address.setStreet1("340 W Market");
        address.setCity("Bloomington");
        address.setState("MN");
        address.setZipcode("55425");
        Geo geo = new Geo();
        geo.setType("Point");
        geo.setCoordinates(new double[]{-93.24565, 44.85466});
        location.setAddress(address);
        location.setGeo(geo);
        theater.setLocation(location);

        Mockito.when(theaterService.getTheaterByTheaterId(1000)).thenReturn(theater);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/theaters/1000")
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.theaterId").value(1000))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.location.address.street1").value("340 W Market"));
    }
}
