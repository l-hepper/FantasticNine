package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.exceptions.CommentNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.Comments;
import com.sparta.doom.fantasticninewebandapi.repositories.CommentsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<Comments> getCommentsByName(String name){
        List<Comments> commentsList = new ArrayList<>();
        for(Comments comment : commentsRepository.findAll()){
            if(comment.getName().equals(name)){
                commentsList.add(comment);
            }
        }
        return commentsList;
    }
    public List<Comments> getCommentsByMovieId(ObjectId id){
        List<Comments> commentsList = new ArrayList<>();
        for(Comments comment : commentsRepository.findAll()){
            if (comment.getMovie_id().equals(id)){
                commentsList.add(comment);
            }
        }
        return commentsList;
    }

    public List<Comments> getCommentsByDateRange(Date startDate, Date endDate){
        List<Comments> commentsList = new ArrayList<>();
        for(Comments comment : commentsRepository.findAll()){
            if(comment.getDate().after(startDate) && comment.getDate().before(endDate)){
                commentsList.add(comment);
            }
        }
        return commentsList;
    }

    public Comments createComment(Comments comment) {

        commentsRepository.save(comment);
        return comment;

    }

    public void deleteComment(ObjectId id) {
        if(commentsRepository.existsById(id)){
            commentsRepository.deleteById(id);
        }
        else throw new CommentNotFoundException("comment not found with id: " + id);
    }

    public Comments updateComment(Comments commentUpdate) {

        if(commentsRepository.existsById(commentUpdate.getId())){
            for(Comments comment : commentsRepository.findAll()){
                if(comment.getId().equals(commentUpdate.getId())){
                    comment = commentUpdate;
                    commentsRepository.save(comment);
                    return comment;
                }
            }
        }
        else {
            throw new CommentNotFoundException("comment not found with id: " + commentUpdate.getId());
        }
        return null;
    }
}
