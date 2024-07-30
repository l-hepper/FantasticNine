package com.sparta.doom.fantasticninewebandapi;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
public class CommentsApiControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @Test
    void testGetComments() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/573a1390f29313caabcd4323/comments"))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testGetCommentByMovie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/573a1390f29313caabcd4323/comments/id/5a9427648b0beebeb69579e7"))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testGetCommentByMovieNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/573a1390f29313caabcd4323/comments/id/5a9427648b0beebeb69579e8"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void testGetCommentByMovieWrongMovie() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/573a1390f29313caabcd446f/comments/id/5a9427648b0beebeb69579e7"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testGetCommentsByDateRange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/573a1390f29313caabcd4323/comments/dates/2015-01-01/2020-01-01"))
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testGetCommentsByDateRangeBeforeDateAfterEndDate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/573a1390f29313caabcd4323/comments/dates/2022-01-01/2020-01-01"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void testGetCommentsByMovieAndUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/573a1390f29313caabcd4323/comments/name/Mercedes-Tyler"))
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("Mercedes Tyler")));
    }
    @Test
    void testGetCommentsByMovieAndUsernameNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/movies/573a1390f29313caabcd4323/comments/name/MercedesTyler"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void testGetCommentsByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/Mercedes-Tyler/comments"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
    void testGetCommentsByUserNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/MercedesTyler/comments"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    @Test
    void testGetCommentsByUserWrongUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/Mercedes-Tyler/comments/dates/2000-01-01/2001-01-01"))
                .andDo(MockMvcResultHandlers.print());
    }


}
