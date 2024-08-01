package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.UserRepository;
import com.sparta.doom.fantasticninewebandapi.security.web.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Web
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserDoc> user = userRepository.findByEmail(email);

        return user
                .map(SecurityUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User: " + email + " not found"));
    }
}
