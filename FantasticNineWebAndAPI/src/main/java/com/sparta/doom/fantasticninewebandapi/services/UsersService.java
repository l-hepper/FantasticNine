package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.exceptions.UserNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDoc createUser(UserDoc userDoc) {
        return userRepository.save(userDoc);
    }

    public Optional<UserDoc> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<UserDoc> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<UserDoc> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserDoc> updateUser(String id, UserDoc updatedUserDoc) {
        if (userRepository.existsById(id)) {
            updatedUserDoc.setId(id);  // Ensure the ID is set correctly
            UserDoc updatedUser = userRepository.save(updatedUserDoc);  // Save and get the updated user
            return Optional.of(updatedUser);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    public boolean deleteUser(String id) {
        userRepository.deleteById(id);
        return getUserById(id).isEmpty();
    }
}
