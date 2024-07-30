package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.security.api.ApiKeyModel;
import com.sparta.doom.fantasticninewebandapi.security.api.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SecurityService implements UserDetailsService {
    private final ApiKeyRepository apiKeyRepository;
    //private final UserRepository userRepository;

    @Autowired
    public SecurityService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    // Api
    public Optional<String> generateKey(String role) {
        if (!role.equals("FULL_ACCESS") && !role.equals("READ_ONLY"))
            return Optional.empty();

        ApiKeyModel apiKey = new ApiKeyModel();
        apiKey.setKey(UUID.randomUUID().toString());
        apiKey.setAccessLevel(role);
        apiKeyRepository.save(apiKey);

        return Optional.of(apiKey.getKey());
    }

    public Optional<String> getRoleFromKey(String apiKey) {
        ApiKeyModel key = apiKeyRepository.findByKey(apiKey);

        if (key == null)
            return Optional.empty();

        return Optional.of(key.getAccessLevel());
    }

    // Web
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
