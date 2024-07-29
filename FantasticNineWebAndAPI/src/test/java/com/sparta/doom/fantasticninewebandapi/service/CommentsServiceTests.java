package com.sparta.doom.fantasticninewebandapi.service;

import com.sparta.doom.fantasticninewebandapi.repositories.CommentsRepository;
import com.sparta.doom.fantasticninewebandapi.services.CommentsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentsServiceTests {


    @Autowired
    CommentsService commentsService;

    @Test
    public void findAllComments(){
        int expected = 41079;
        int actual = commentsService.getAllComments().size();
        Assertions.assertEquals(expected, actual);
    }


}
