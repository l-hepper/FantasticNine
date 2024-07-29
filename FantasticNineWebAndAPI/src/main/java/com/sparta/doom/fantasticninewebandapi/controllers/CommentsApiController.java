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
@RequestMapping("/api/movies/")
public class CommentsApiController {

    private final CommentsService commentsService;

    @Autowired
    public CommentsApiController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/{movie}/comments/")
    public ResponseEntity<CollectionModel<EntityModel<Comments>>> getComments(@PathVariable("movie") ObjectId movie) {
        List<Comments> comments = commentsService.getCommentsByMovieId(movie);
        return new ResponseEntity<>(CollectionModel.of(comments, HttpStatus.OK));
    }

    @PostMapping("/{movie}/comments/create/")
    public Comments createComment(@PathVariable("movie") ObjectId movieId, @RequestBody Comments comments) {

    }
}
