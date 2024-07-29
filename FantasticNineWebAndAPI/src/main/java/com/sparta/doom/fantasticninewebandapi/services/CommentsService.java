package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.exceptions.CommentNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.Comments;
import com.sparta.doom.fantasticninewebandapi.repositories.CommentsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
            if (comment.getId().equals(id)){
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

    public void createComment(Comments comments) {
        commentsRepository.save(comments);
    }

    public void deleteComment(ObjectId id) {
        commentsRepository.deleteById(id);
    }

    public void updateComment(ObjectId id, Comments comments) {
        for(Comments comment : commentsRepository.findAll()){
            if(comment.getId().equals(id)){
                commentsRepository.save(comment);
            }
        }
    }
}
