package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.exceptions.CommentNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.CommentDoc;
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

    public List<CommentDoc> getAllComments() {
        return commentsRepository.findAll();
    }

    public CommentDoc getCommentById(ObjectId id) {
        return commentsRepository.findById(id).orElseThrow(()-> new CommentNotFoundException("comment not found with id: " + id));
    }
    public List<CommentDoc> getCommentsByName(String name){
        List<CommentDoc> commentDocList = new ArrayList<>();
        for(CommentDoc comment : commentsRepository.findAll()){
            if(comment.getName().equals(name)){
                commentDocList.add(comment);
            }
        }
        return commentDocList;
    }
    public List<CommentDoc> getCommentsByMovieId(ObjectId id){
        List<CommentDoc> commentDocList = new ArrayList<>();
        for(CommentDoc comment : commentsRepository.findAll()){
            if (comment.getMovie_id().equals(id)){
                commentDocList.add(comment);
            }
        }
        return commentDocList;
    }

    public List<CommentDoc> getCommentsByDateRange(Date startDate, Date endDate){
        List<CommentDoc> commentDocList = new ArrayList<>();
        for(CommentDoc comment : commentsRepository.findAll()){
            if(comment.getDate().after(startDate) && comment.getDate().before(endDate)){
                commentDocList.add(comment);
            }
        }
        return commentDocList;
    }

    public CommentDoc createComment(CommentDoc comment) {

        commentsRepository.save(comment);
        return comment;

    }


    public void deleteComment(ObjectId id) {
        if(commentsRepository.existsById(id)){
            commentsRepository.deleteById(id);
        }
        else throw new CommentNotFoundException("comment not found with id: " + id);
    }

    public CommentDoc updateComment(CommentDoc commentUpdate) {

        if(commentsRepository.existsById(commentUpdate.getId())){
            for(CommentDoc comment : commentsRepository.findAll()){
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
