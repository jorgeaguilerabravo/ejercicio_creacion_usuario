package com.integracion.usuarios.service;

import com.integracion.usuarios.entities.UserEntity;
import com.integracion.usuarios.model.Response;
import com.integracion.usuarios.repository.UserRepository;
import com.integracion.usuarios.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public Response createUser(UserEntity user) {
        user.setToken(jwtUtil.generateToken(user.getName()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        Response response = new Response();
        response.setId(user.getId());
        response.setCreated(new Date());
        response.setModified(new Date());
        response.setLastLogin(new Date());
        response.setIsActive(Boolean.TRUE);
        response.setToken(user.getToken());
        return response;
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
