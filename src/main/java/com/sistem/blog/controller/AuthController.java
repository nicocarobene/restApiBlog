package com.sistem.blog.controller;

import com.sistem.blog.DTO.JwtAuthResponseDTO;
import com.sistem.blog.DTO.LoginDTO;
import com.sistem.blog.DTO.RegisterDTO;
import com.sistem.blog.Security.JwtTokenProvider;
import com.sistem.blog.model.Rol;
import com.sistem.blog.model.User;
import com.sistem.blog.repository.RolRespository;
import com.sistem.blog.repository.UserRespository;
import com.sistem.blog.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO){
        return authService.register(registerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDTO> authenticateUser(@RequestBody LoginDTO loginDTO ){
        return authService.login(loginDTO);
    }


}
