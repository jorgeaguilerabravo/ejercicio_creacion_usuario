package com.integracion.usuarios.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<PhoneEntity> phones;

    private String token;

    public UserEntity() {
        this.id = UUID.randomUUID();
    }


}
