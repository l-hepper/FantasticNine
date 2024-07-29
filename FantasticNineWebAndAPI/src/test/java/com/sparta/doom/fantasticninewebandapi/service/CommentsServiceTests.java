package com.sparta.doom.fantasticninewebandapi.service;

import com.sparta.doom.fantasticninewebandapi.exceptions.CommentNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.Comments;
import com.sparta.doom.fantasticninewebandapi.repositories.CommentsRepository;
import com.sparta.doom.fantasticninewebandapi.services.CommentsService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CommentsServiceTests {

    @Mock
    CommentsRepository commentsRepository;

    @InjectMocks
    CommentsService service;

    private Comments commentOne;
    private Comments commentTwo;
    private ObjectId id;
    private ObjectId id2;

    @Autowired
    private CommentsService commentsService;

    @BeforeEach()
    public void setUp() {
        id = new ObjectId();
        commentOne = new Comments();
        commentOne.setId(id);
        commentOne.setDate(new Date());
        commentOne.setEmail("test@test.com");
        commentOne.setMovie_id(new ObjectId());
        commentOne.setText("This is some sample text");
        commentOne.setName("Testing Account");
    }

    @Test
    public void findAllComments(){
        int expected = 41079;
        int actual = commentsService.getAllComments().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findCommentById(){
        Comments expected = commentOne;
        when(commentsRepository.findById(id)).thenReturn(Optional.of(commentOne));
        Comments actual = service.getCommentById(id);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void commentNotFoundExceptionThrown(){
        when(commentsRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> service.getCommentById(id));
    }

    @Test
    public void findCommentByUser(){

    }

    @Test
    public void findCommentByMovieId(){

    }
    @Test
    public void findCommentsByDateRange(){

    }
    @Test
    public void updateComment(){

    }
    @Test
    public void deleteComment(){

    }
    @Test
    public void createNewComment(){

    }


}
