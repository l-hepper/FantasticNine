package com.sparta.doom.fantasticninewebandapi.controllers;

import com.sparta.doom.fantasticninewebandapi.models.CommentDoc;
import com.sparta.doom.fantasticninewebandapi.services.CommentsService;
import com.sparta.doom.fantasticninewebandapi.services.SecurityService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
public class CommentsApiController {

    private final CommentsService commentsService;
    private final SecurityService securityService;

    @Autowired
    public CommentsApiController(CommentsService commentsService, SecurityService securityService) {
        this.commentsService = commentsService;
        this.securityService = securityService;
    }

    @GetMapping("/movies/{movie}/comments")
    public ResponseEntity<CollectionModel<CommentDoc>> getComments(@PathVariable("movie") ObjectId movie) {
        List<CommentDoc> comments = commentsService.getCommentsByMovieId(movie);
        return new ResponseEntity<>(CollectionModel.of(comments), HttpStatus.OK);
    }

    @GetMapping("/movies/{movie}/comments/{commentId}")
    public ResponseEntity<CommentDoc> getComment(@PathVariable("movie") ObjectId movie, @PathVariable("commentId") ObjectId commentId) {
        CommentDoc comment = commentsService.getCommentById(commentId);
        if (comment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!comment.getMovie_id().equals(movie)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/movies/{movie}/comments/{date1}/{date2}")
    public ResponseEntity<CollectionModel<CommentDoc>> getCommentsByMovieAndDate(@PathVariable("movie") ObjectId movie
            , @PathVariable Date date1, @PathVariable Date date2) {
        List<CommentDoc> comments = commentsService.getCommentsByMovieId(movie);
        comments = comments.stream().filter(c-> commentsService.getCommentsByDateRange(date1,date2).contains(c)).toList();
        return new ResponseEntity<>(CollectionModel.of(comments), HttpStatus.OK);
    }

    @GetMapping("/movies/{movie}/comments/{name}")
    public ResponseEntity<CollectionModel<CommentDoc>> getCommentsByMovieAndUsername(@PathVariable("movie") ObjectId movie, @PathVariable("name") String username) {
        List<CommentDoc> comments = commentsService.getCommentsByName(username);
        comments = comments.stream().filter(c -> c.getMovie_id().equals(movie)).toList();
        return new ResponseEntity<>(CollectionModel.of(comments), HttpStatus.OK);
    }

    @GetMapping("/users/{username}/comments")
    public ResponseEntity<CollectionModel<CommentDoc>> getCommentsByUsername(@PathVariable("username") String username) {
        List<CommentDoc> comments = commentsService.getCommentsByName(username);
        return new ResponseEntity<>(CollectionModel.of(comments), HttpStatus.OK);
    }

    @GetMapping("/users/{username}/comments/{date1}/{date2}")
    public ResponseEntity<CollectionModel<CommentDoc>> getCommentsByUsernameAndDateRange(@PathVariable("username") String username
            , @PathVariable("date1") Date date1, @PathVariable("date2") Date date2) {
        List<CommentDoc> comments = commentsService.getCommentsByName(username);
        comments = comments.stream().filter(c -> commentsService.getCommentsByDateRange(date1, date2).contains(c)).toList();
        return new ResponseEntity<>(CollectionModel.of(comments), HttpStatus.OK);
    }

    @PostMapping("/{movie}/comments/create")
    public ResponseEntity<EntityModel<CommentDoc>> createComment(@RequestHeader(name = "DOOM-API-KEY") String key,@PathVariable("movie") ObjectId movieId, @RequestBody CommentDoc newComment) {
        Optional<String> requestRole = securityService.getRoleFromKey(key);
        if (requestRole.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No key found");
        } else if (!requestRole.get().equals("FULL_ACCESS")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        if(!newComment.getId().equals(movieId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentsService.createComment(newComment);
        URI location = URI.create("/api/movies/" + movieId + "/comments/" + newComment.getId());
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CommentsApiController.class).getComment(movieId,newComment.getId())).withSelfRel();
        return ResponseEntity.created(location).body(EntityModel.of(newComment).add(selfLink));
    }
    @PutMapping("/{movie}/comments/{commentId}")
    public ResponseEntity<CommentDoc> updateComment(@RequestHeader(name = "DOOM-API-KEY") String key,@PathVariable("movie") ObjectId movieId, @PathVariable("commentId") ObjectId commentId, @RequestBody CommentDoc newComment) {
        Optional<String> requestRole = securityService.getRoleFromKey(key);
        if (requestRole.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No key found");
        } else if (!requestRole.get().equals("FULL_ACCESS")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        CommentDoc oldComment = commentsService.getCommentById(commentId);
        if(oldComment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(!oldComment.getMovie_id().equals(movieId)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        commentsService.updateComment(newComment);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/{movie}/comments/{commentId}")
    public ResponseEntity<CommentDoc> deleteComment(@RequestHeader(name = "DOOM-API-KEY") String key, @PathVariable("commentId") ObjectId commentId) {
        Optional<String> requestRole = securityService.getRoleFromKey(key);
        if (requestRole.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No key found");
        } else if (!requestRole.get().equals("FULL_ACCESS")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        CommentDoc comment = commentsService.getCommentById(commentId);
        if(comment == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentsService.deleteComment(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
