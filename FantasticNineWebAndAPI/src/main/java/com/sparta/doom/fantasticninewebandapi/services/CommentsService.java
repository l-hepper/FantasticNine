package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.exceptions.CommentNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.Comments;
import com.sparta.doom.fantasticninewebandapi.repositories.CommentsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsService {

    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public List<Comments> getAllComments() {
        return commentsRepository.findAll();
    }

    public Comments getCommentById(ObjectId id) {
        return commentsRepository.findById(id).orElseThrow(()-> new CommentNotFoundException("comment not found with id: " + id));
    }

    public void createComment(Comments comments) {
        commentsRepository.save(comments);
    }

//    public Comments getCommentById(ObjectId id) {
//        return commentsRepository.findAllById(id);
//    }

    //todo find comments by certain user
    // find comments per movie
    // find comments in date range


}
