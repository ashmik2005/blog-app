package com.jbdl.blogapp.controller;


import com.jbdl.blogapp.entity.Role;
import com.jbdl.blogapp.entity.User;
import com.jbdl.blogapp.payload.LoginDto;
import com.jbdl.blogapp.payload.SignUpDto;
import com.jbdl.blogapp.repository.RoleRepository;
import com.jbdl.blogapp.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticate(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Signed in successfully", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignUpDto signUpDto) {
        // check if username or email already exists
        if ((userRepository.existsByUsername(signUpDto.getUsername())) ||
             userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Username or email already in use!", HttpStatus.BAD_REQUEST);
        }

        // Convert SignUpDto to user object (so that we can save it in DB)
        User user = new User();
        user.setName(signUpDto.getName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setUsername(signUpDto.getUsername());

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", HttpStatus.OK);

    }




}
