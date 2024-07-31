package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.UserRepository;
import com.sparta.doom.fantasticninewebandapi.security.api.ApiKeyModel;
import com.sparta.doom.fantasticninewebandapi.security.api.ApiKeyRepository;
import com.sparta.doom.fantasticninewebandapi.security.web.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class    SecurityService implements UserDetailsService {
    private final ApiKeyRepository apiKeyRepository;
    private final UserRepository userRepository;

    @Autowired
    public SecurityService(ApiKeyRepository apiKeyRepository, UserRepository userRepository) {
        this.apiKeyRepository = apiKeyRepository;
        this.userRepository = userRepository;
    }

    // Api
    public Optional<String> generateKey(String role) {
        if (!role.equals("FULL_ACCESS") && !role.equals("READ_ONLY"))
            return Optional.empty();

        ApiKeyModel apiKey = new ApiKeyModel();
        apiKey.setKey(UUID.randomUUID().toString());
        apiKey.setAccessLevel(role);
        apiKey = apiKeyRepository.save(apiKey);

        return Optional.of(apiKey.getKey());
    }

    public Optional<String> getRoleFromKey(String apiKey) {
        Optional<ApiKeyModel> key = apiKeyRepository.findByKey(apiKey);

        return key.map(ApiKeyModel::getAccessLevel);
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
