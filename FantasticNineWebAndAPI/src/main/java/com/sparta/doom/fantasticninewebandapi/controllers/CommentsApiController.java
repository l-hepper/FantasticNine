package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.models.Comments;
import com.sparta.doom.fantasticninewebandapi.services.CommentsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentsApiController {

    private final CommentsService commentsService;

    @Autowired
    public CommentsApiController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/movies/{movie}/comments/")
    public ResponseEntity<CollectionModel<Comments>> getComments(@PathVariable("movie") ObjectId movie) {
        List<Comments> comments = commentsService.getCommentsByMovieId(movie);
        return new ResponseEntity<>(CollectionModel.of(comments), HttpStatus.OK);
    }

    @GetMapping("/movies/{movie}/comments/{name}")
    public ResponseEntity<CollectionModel<Comments>> getCommentsByMovieAndUsername(@PathVariable("movie") ObjectId movie, @PathVariable("name") String username) {
        List<Comments> comments = commentsService.getCommentsByName(username);
        comments = comments.stream().filter(c -> c.getMovie_id().equals(movie)).toList();
        return new ResponseEntity<>(CollectionModel.of(comments), HttpStatus.OK);
    }

    @GetMapping("/users/{username}/comments")
    public ResponseEntity<CollectionModel<Comments>> getCommentsByUsername(@PathVariable("username") String username) {
        List<Comments> comments = commentsService.getCommentsByName(username);
        return new ResponseEntity<>(CollectionModel.of(comments), HttpStatus.OK);
    }
//    @PostMapping("/{movie}/comments/create/")
//    public Comments createComment(@PathVariable("movie") ObjectId movieId, @RequestBody Comments comments) {
//    }
}
