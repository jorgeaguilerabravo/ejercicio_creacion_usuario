package com.integracion.usuarios.controller;

import com.integracion.usuarios.entities.UserEntity;
import com.integracion.usuarios.model.MessageResponse;
import com.integracion.usuarios.service.UserService;
import com.integracion.usuarios.util.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestValidator requestValidator;

    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserEntity user) {
        MessageResponse messageResponse = requestValidator.validateRequest(user);
        if(messageResponse.getMensaje() == null){
            return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }

    }
}
