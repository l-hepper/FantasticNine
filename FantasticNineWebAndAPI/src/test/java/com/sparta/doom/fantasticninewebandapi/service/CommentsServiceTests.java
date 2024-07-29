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

import java.util.*;

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
    private Comments commentThree;
    private ObjectId id;
    private ObjectId id2;
    private ObjectId movieId1;
    private ObjectId movieId2;
    private Date startDate;
    private Date endDate;

    @Autowired
    private CommentsService commentsService;

    @BeforeEach()
    public void setUp() {

        Calendar calendar = Calendar.getInstance();

        id = new ObjectId();
        movieId1 = new ObjectId();
        commentOne = new Comments();
        commentOne.setId(id);
        calendar.set(2001,Calendar.JANUARY,1,0,0,0);
        commentOne.setDate(calendar.getTime());
        commentOne.setEmail("test@test.com");
        commentOne.setMovie_id(movieId1);
        commentOne.setText("This is some sample text");
        commentOne.setName("Testing Account");

        movieId2 = new ObjectId();
        id2 = new ObjectId();
        commentTwo = new Comments();
        commentTwo.setId(id2);
        calendar.set(2002,Calendar.JANUARY,1,0,0,0);
        commentTwo.setDate(calendar.getTime());
        commentTwo.setEmail("test2@test2.com");
        commentTwo.setMovie_id(movieId2);
        commentTwo.setText("This is some sample text again");
        commentTwo.setName("Testing Account2");

        commentThree = new Comments();
        commentThree.setId(id);
        calendar.set(2001,Calendar.JANUARY,1,0,0,0);
        commentThree.setDate(calendar.getTime());
        commentThree.setEmail("test@test.com");
        commentThree.setMovie_id(movieId1);
        commentThree.setText("This is some sample text that has been changed");
        commentThree.setName("Testing Account");




        calendar.set(2001,Calendar.MAY,1,0,0,0);
        startDate = calendar.getTime();
        calendar.set(2002,Calendar.MAY,1,0,0,0);
        endDate = calendar.getTime();
    }

    @Test
    public void findAllComments(){
        int expected = 2;
        List<Comments> commentList = new ArrayList<>();
        commentList.add(commentOne);
        commentList.add(commentTwo);
        when(commentsRepository.findAll()).thenReturn(commentList);
        List<Comments> actual = service.getAllComments();
        Assertions.assertEquals(expected, actual.size());
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
    public void findCommentsByUsername(){
        List<Comments> expected = Arrays.asList(commentOne);
        List<Comments> commentList = new ArrayList<>();
        commentList.add(commentOne);
        commentList.add(commentTwo);
        when(commentsRepository.findAll()).thenReturn(commentList);
        List<Comments> actual = service.getCommentsByName("Testing Account");
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findCommentByMovieId(){
        List<Comments> expected = Arrays.asList(commentTwo);
        List<Comments> commentList = new ArrayList<>();
        commentList.add(commentOne);
        commentList.add(commentTwo);
        when(commentsRepository.findAll()).thenReturn(commentList);
        List<Comments> actual = service.getCommentsByMovieId(commentTwo.getMovie_id());
        Assertions.assertEquals(expected, actual);

    }

    @Test
    public void findCommentsByDateRange(){
        List<Comments> expected = Arrays.asList(commentTwo);
        List<Comments> commentList = new ArrayList<>();
        commentList.add(commentOne);
        commentList.add(commentTwo);
        when(commentsRepository.findAll()).thenReturn(commentList);
        List<Comments> actual = service.getCommentsByDateRange(startDate, endDate);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void updateComment(){
        List<Comments> commentList = new ArrayList<>();
        commentList.add(commentOne);
        commentList.add(commentTwo);
        when(commentsRepository.findById(id)).thenReturn(Optional.of(commentThree));
        when(commentsRepository.findAll()).thenReturn(commentList);
        when(commentsRepository.save(commentOne)).thenReturn(commentThree);
        String expected = "This is some sample text that has been changed";
        service.updateComment(commentThree);
        Comments updated = service.getCommentById(id);
        String actual = updated.getText();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void deleteComment(){

    }

    @Test
    public void createNewComment(){
    }
}
