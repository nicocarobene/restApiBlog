package com.sistem.blog.controller;

import com.sistem.blog.DTO.LoginDTO;
import com.sistem.blog.DTO.RegisterDTO;
import com.sistem.blog.model.Rol;
import com.sistem.blog.model.User;
import com.sistem.blog.repository.RolRespository;
import com.sistem.blog.repository.UserRespository;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRespository userEM;
    @Autowired
    private RolRespository rolEM;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterDTO registerDTO){
        System.out.println("email: "+registerDTO.getEmail()+". name: "+registerDTO.getName()+" . username: "+ registerDTO.getUsername()+ ". password: "+registerDTO.getPassword());



        if(userEM.existsByUsername(registerDTO.getUsername())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username was already exists");
        } else if (userEM.existsByEmail(registerDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email was already exists");
        }
        User newUser= new User();
        newUser.setUsername(registerDTO.getUsername());
        newUser.setName(registerDTO.getName());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Rol rol= rolEM.findByName("ROLE_USER").get();
        System.out.println("holaaaa    "+rol.getName());
        newUser.setRol(Collections.singleton(rol));
        userEM.save(newUser);
        return ResponseEntity.status(HttpStatus.OK).body("successfully registered user");
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDTO ){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.status(HttpStatus.OK).body("Have successfully logged in");
    }


}
