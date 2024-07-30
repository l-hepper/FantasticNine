package com.sparta.doom.fantasticninewebandapi.services;

import com.sparta.doom.fantasticninewebandapi.exceptions.UserNotFoundException;
import com.sparta.doom.fantasticninewebandapi.models.UserDoc;
import com.sparta.doom.fantasticninewebandapi.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UsersService usersService;

    private UserDoc userDoc;
    private String userId = "1";
    private String userEmail = "user@example.com";

    @BeforeEach
    void setUp() {
        userDoc = new UserDoc(userEmail, "John Doe", "password", Set.of("USER"));
        userDoc.setId(userId);
    }

    @Test
    void givenUserInfoCreatesNewUser() {
        when(userRepository.save(any(UserDoc.class))).thenReturn(userDoc);

        UserDoc result = usersService.createUser(userDoc);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).save(userDoc);
    }

    @Test
    void givenValidUserIdReturnsUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(userDoc));

        Optional<UserDoc> result = usersService.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void givenInvalidUserIdReturnsUser() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<UserDoc> result = usersService.getUserById(userId);

        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void givenValidEmailReturnsUser() {
        when(userRepository.findByEmail(userEmail)).thenReturn(Optional.of(userDoc));

        Optional<UserDoc> result = usersService.getUserByEmail(userEmail);

        assertTrue(result.isPresent());
        assertEquals(userEmail, result.get().getEmail());
        verify(userRepository, times(1)).findByEmail(userEmail);
    }

    @Test
    void returnsAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(userDoc)); // Corrected to List.of

        Iterable<UserDoc> result = usersService.getAllUsers();

        assertNotNull(result);
        assertTrue(result.iterator().hasNext());
        assertEquals(userId, result.iterator().next().getId());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void givenValidUserIdReturnsUpdatedUser() {
        when(userRepository.existsById(userId)).thenReturn(true);

        UserDoc updatedUser = new UserDoc(userEmail, "John Smith", "newpassword", Set.of("USER"));
        updatedUser.setId(userId);

        when(userRepository.save(any(UserDoc.class))).thenReturn(updatedUser);

        Optional<UserDoc> result = usersService.updateUser(userId, updatedUser);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals("John Smith", result.get().getName());
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void givenInvalidUserIdThrowsException() {
        when(userRepository.existsById(userId)).thenReturn(false);

        UserDoc updatedUser = new UserDoc(userEmail, "John Smith", "newpassword", Set.of("USER"));

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            usersService.updateUser(userId, updatedUser);
        });

        assertEquals("User not found with id: " + userId, thrown.getMessage());
        verify(userRepository, times(1)).existsById(userId);
    }

    @Test
    void givenValidUserIdReturnsTrue() {
        doNothing().when(userRepository).deleteById(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        boolean result = usersService.deleteUser(userId);

        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void givenInvalidUserIdReturnsFalse() {
        when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(userDoc));

        boolean result = usersService.deleteUser(userId);

        assertFalse(result);
        verify(userRepository, times(1)).findById(userId);
    }
}
