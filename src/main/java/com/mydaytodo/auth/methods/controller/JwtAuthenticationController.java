package com.mydaytodo.auth.methods.controller;

import com.mydaytodo.auth.methods.model.AuthRequest;
import com.mydaytodo.auth.methods.service.JwtService;
import com.mydaytodo.auth.methods.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 * Used
 * https://www.javainuse.com/spring/boot-jwt
 * https://www.javainuse.com/spring/jwt
 * https://www.geeksforgeeks.org/spring-boot-3-0-jwt-authentication-with-spring-security-using-mysql-database/
 * and Meta AI to learn about implementing JWT in Spring Boot
 */
@RestController
@Slf4j
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/welcome")
    public ResponseEntity<String> greet() throws Exception {
        return new ResponseEntity<>("Welcome to JWT Auth", HttpStatus.OK);
    }

    /**
     * Endpoint to register new users
     * 1. validate the request and body
     * 2. insert the user
     * @param authRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody AuthRequest authRequest) {
        log.info("Go a request to register user {}", authRequest.toString());
        User user = userInfoService.addUser(authRequest);
        log.info("successfully added user {}", user.toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        log.info("Called generateToken API and about to generate authenticate via authentication manager");

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        log.info("Auth object {}", Boolean.toString(authentication.isAuthenticated()));
        if (authentication.isAuthenticated()) {
            log.info("Authentication successful");
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            log.info("Authentication NOT successful");
            throw new UsernameNotFoundException("invalid user request");
        }
    }
}
