package com.integracion.usuarios.util;

import com.integracion.usuarios.entities.UserEntity;
import com.integracion.usuarios.model.MessageResponse;
import com.integracion.usuarios.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordValidator passwordValidator;

    private static final String MAIL_REGISTRADO = "El correo ya se encuentra registrado";

    private static final String PASSWORD_ERROR = "La contraseña debe tener al menos 8 caracteres";

    public MessageResponse validateRequest(UserEntity user){

        MessageResponse messageResponse = new MessageResponse();

        if (userService.findUserByEmail(user.getEmail()) != null) {
            messageResponse.setMensaje(MAIL_REGISTRADO);
        } else if(passwordValidator.validatePassword(user.getPassword()) == Boolean.FALSE) {
            messageResponse.setMensaje(PASSWORD_ERROR);
        }

        return messageResponse;
    }
}
