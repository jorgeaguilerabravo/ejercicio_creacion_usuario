package com.integracion.usuarios.service;

import com.integracion.usuarios.entities.UserEntity;
import com.integracion.usuarios.model.Response;
import com.integracion.usuarios.repository.UserRepository;
import com.integracion.usuarios.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserShouldReturnResponse() {
        UserEntity user = new UserEntity();
        user.setName("Test User");
        user.setPassword("password");
        user.setEmail("test@test.com");

        when(jwtUtil.generateToken(user.getName())).thenReturn("token");
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        Response response = userService.createUser(user);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertTrue(response.getIsActive());
    }

    @Test
    public void findUserByEmailShouldReturnUser() {
        UserEntity user = new UserEntity();
        user.setName("Test User");
        user.setPassword("password");
        user.setEmail("test@test.com");

        when(userRepository.findByEmail("test@test.com")).thenReturn(user);

        UserEntity foundUser = userService.findUserByEmail("test@test.com");

        assertNotNull(foundUser);
        assertEquals("Test User", foundUser.getName());
    }

    @Test
    public void findUserByEmailShouldReturnNullWhenUserNotFound() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(null);

        UserEntity foundUser = userService.findUserByEmail("test@test.com");

        assertNull(foundUser);
    }
}