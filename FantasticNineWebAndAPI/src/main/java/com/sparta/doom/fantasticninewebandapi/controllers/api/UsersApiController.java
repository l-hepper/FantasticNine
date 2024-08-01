package com.sparta.doom.fantasticninewebandapi.controllers.api;

import com.sparta.doom.fantasticninewebandapi.exceptions.UserNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import com.sparta.doom.fantasticninewebandapi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersApiController {

    private final UsersService usersService;

    @Autowired
    public UsersApiController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<UserDoc> createUser(@RequestBody UserDoc userDoc) {
        // Check if the authenticated user has FULL_ACCESS
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            UserDoc createdUser = usersService.createUser(userDoc);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDoc> getUserById(@PathVariable String id) {
        Optional<UserDoc> userDoc = usersService.getUserById(id);
        return userDoc
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDoc> getUserByEmail(@PathVariable String email) {
        Optional<UserDoc> userDoc = usersService.getUserByEmail(email);
        return userDoc.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @GetMapping
    public ResponseEntity<Iterable<UserDoc>> getAllUsers() {
        // Check if the authenticated user has FULL_ACCESS
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            Iterable<UserDoc> users = usersService.getAllUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDoc> updateUser(@PathVariable String id, @RequestBody UserDoc updatedUserDoc) {
        // Check if the authenticated user has FULL_ACCESS
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            Optional<UserDoc> updatedUser = usersService.updateUser(id, updatedUserDoc);
            return updatedUser
                    .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        // Check if the authenticated user has FULL_ACCESS
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        if (userDetails.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            boolean deleted = usersService.deleteUser(id);
            if (deleted) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                throw new UserNotFoundException("User not found with id: " + id);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
