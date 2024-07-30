package com.sparta.doom.fantasticninewebandapi.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}