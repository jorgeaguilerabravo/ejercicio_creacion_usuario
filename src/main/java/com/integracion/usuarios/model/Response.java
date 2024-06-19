package com.integracion.usuarios.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Response {

    private UUID id;
    private Date created;
    private Date modified;
    private Date lastLogin;
    private Boolean isActive;
    private String token;
}
