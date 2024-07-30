package com.sparta.doom.fantasticninewebandapi.controllers.api;

import com.sparta.doom.fantasticninewebandapi.exceptions.UserNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import com.sparta.doom.fantasticninewebandapi.services.SecurityService;
import com.sparta.doom.fantasticninewebandapi.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final SecurityService securityService;

    private final UsersService usersService;

    @Autowired
    public UsersController(SecurityService securityService, UsersService usersService) {
        this.securityService = securityService;
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<UserDoc> createUser(@RequestHeader(name = "DOOM-API-KEY") String key, @RequestBody UserDoc userDoc) {
        Optional<String> requestRole = securityService.getRoleFromKey(key);
        if (requestRole.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No key found");
        } else if (!requestRole.get().equals("FULL_ACCESS")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        UserDoc createdUser = usersService.createUser(userDoc);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDoc> getUserById(@PathVariable String id) {
        Optional<UserDoc> userDoc = usersService.getUserById(id);
        return userDoc
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @GetMapping
    public ResponseEntity<Iterable<UserDoc>> getAllUsers(@RequestHeader(name = "DOOM-API-KEY") String key) {
        Optional<String> requestRole = securityService.getRoleFromKey(key);
        if (requestRole.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No key found");
        } else if (!requestRole.get().equals("FULL_ACCESS")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Iterable<UserDoc> users = usersService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDoc> updateUser(@RequestHeader(name = "DOOM-API-KEY") String key, @PathVariable String id, @RequestBody UserDoc updatedUserDoc) {
        Optional<String> requestRole = securityService.getRoleFromKey(key);
        if (requestRole.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No key found");
        } else if (!requestRole.get().equals("FULL_ACCESS")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        Optional<UserDoc> updatedUser = usersService.updateUser(id, updatedUserDoc);
        return updatedUser
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestHeader(name = "DOOM-API-KEY") String key, @PathVariable String id) {
        Optional<String> requestRole = securityService.getRoleFromKey(key);
        if (requestRole.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No key found");
        } else if (!requestRole.get().equals("FULL_ACCESS")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        boolean deleted = usersService.deleteUser(id);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
}
