package com.integracion.usuarios.controller;

import com.integracion.usuarios.entities.UserEntity;
import com.integracion.usuarios.model.MessageResponse;
import com.integracion.usuarios.model.Response;
import com.integracion.usuarios.service.UserService;
import com.integracion.usuarios.util.RequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private RequestValidator requestValidator;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserShouldReturnCreatedStatusWhenValidationPasses() {
        Response response = new Response();
        response.setId(UUID.randomUUID());
        response.setCreated(new Date());
        response.setModified(new Date());
        response.setLastLogin(new Date());
        response.setIsActive(Boolean.TRUE);
        response.setToken("token");

        UserEntity user = new UserEntity();
        user.setName("Test User");
        user.setPassword("password");
        user.setEmail("test@test.com");

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMensaje(null);

        when(requestValidator.validateRequest(user)).thenReturn(messageResponse);
        when(userService.createUser(user)).thenReturn(response);

        ResponseEntity<Object> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void createUserShouldReturnBadRequestStatusWhenValidationFails() {
        UserEntity user = new UserEntity();
        user.setName("Test User");
        user.setPassword("password");
        user.setEmail("test@test.com");

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMensaje("Validation Error");

        when(requestValidator.validateRequest(user)).thenReturn(messageResponse);

        ResponseEntity<Object> responseEntity = userController.createUser(user);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}