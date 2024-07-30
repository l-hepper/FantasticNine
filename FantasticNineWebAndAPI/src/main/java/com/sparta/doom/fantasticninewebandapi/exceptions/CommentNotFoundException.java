package com.sparta.doom.fantasticninewebandapi.exceptions;

public class CommentNotFoundException extends RuntimeException{
    public CommentNotFoundException(String message){
        super(message);
    }
}
