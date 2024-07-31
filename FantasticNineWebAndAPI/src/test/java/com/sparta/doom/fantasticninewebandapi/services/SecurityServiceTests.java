package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.UserRepository;
import com.sparta.doom.fantasticninewebandapi.security.api.ApiKeyModel;
import com.sparta.doom.fantasticninewebandapi.security.api.ApiKeyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SecurityServiceTests {

    @Mock
    private ApiKeyRepository apiKeyRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SecurityService securityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidRoleGenerateKeyReturnsNewKey() {
        String role = "FULL_ACCESS";
        String generatedKey = UUID.randomUUID().toString();
        ApiKeyModel apiKey = new ApiKeyModel();
        apiKey.setKey(generatedKey);
        apiKey.setAccessLevel(role);

        when(apiKeyRepository.save(any(ApiKeyModel.class))).thenReturn(apiKey);

        Optional<String> newKey = securityService.generateKey(role);
        Assertions.assertTrue(newKey.isPresent());
        Assertions.assertEquals(generatedKey, newKey.get());
    }

    @Test
    public void givenInvalidRoleGenerateKeyReturnsNewKey() {
        Optional<String> newKey = securityService.generateKey("INVALID_ROLE");
        Assertions.assertTrue(newKey.isEmpty());
    }

    @Test
    public void givenValidKeyGetRoleFromKeyReturnsRole() {
        String apiKey = "unique-api-key-456";
        String role = "FULL_ACCESS";
        ApiKeyModel apiKeyModel = new ApiKeyModel();
        apiKeyModel.setKey(apiKey);
        apiKeyModel.setAccessLevel(role);

        when(apiKeyRepository.findByKey(apiKey)).thenReturn(Optional.of(apiKeyModel));

        Optional<String> retrievedRole = securityService.getRoleFromKey(apiKey);
        Assertions.assertTrue(retrievedRole.isPresent());
        Assertions.assertEquals(role, retrievedRole.get());
    }

    @Test
    public void givenInvalidKeyGetRoleFromKeyReturnsRole() {
        when(apiKeyRepository.findByKey("not-a-valid-key")).thenReturn(Optional.empty());

        Optional<String> retrievedRole = securityService.getRoleFromKey("not-a-valid-key");
        Assertions.assertTrue(retrievedRole.isEmpty());
    }

    @Test
    public void givenValidEmailLoadByUsernameDoesntThrowException() {
        String email = "jUser@email.com";
        UserDoc userDoc = new UserDoc(email, "John User", "password", new HashSet<>()); // Assume this has the necessary setup
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userDoc));

        Assertions.assertDoesNotThrow(() -> securityService.loadUserByUsername(email));
    }

    @Test
    public void givenInvalidEmailLoadByUsernameThrowsException() {
        String email = "invalidUser@email.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(UsernameNotFoundException.class,
                () -> securityService.loadUserByUsername(email));
    }
}
