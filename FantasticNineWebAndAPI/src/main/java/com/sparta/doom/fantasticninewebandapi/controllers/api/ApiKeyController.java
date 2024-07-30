package com.sparta.doom.fantasticninewebandapi.controllers.api;

import com.sparta.doom.fantasticninewebandapi.exceptions.InvalidRoleException;
import com.sparta.doom.fantasticninewebandapi.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/keygen")
public class ApiKeyController {
    private final SecurityService securityService;

    @Autowired
    public ApiKeyController(final SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public ResponseEntity<String> generateApiKey(@RequestHeader(name = "DOOM-API-KEY") String key, @RequestParam String role) {
        Optional<String> requestRole = securityService.getRoleFromKey(key); // secure API endpoints like this
        if (requestRole.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No key found");
        } else if (!requestRole.get().equals("FULL_ACCESS")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }

        String newKey = securityService.generateKey(role).orElseThrow(() -> new InvalidRoleException(role + ": Invalid role provided"));

        return ResponseEntity.ok(newKey);
    }
}
