package com.integracion.usuarios.controller;

import com.integracion.usuarios.model.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        MessageResponse messageResponse = new MessageResponse();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        if(Objects.equals(fieldErrors.get(0).getField(),"email")){
            messageResponse.setMensaje(fieldErrors.get(0).getDefaultMessage());
        }
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }
}
