package com.mydaytodo.auth.methods.controller;

import com.mydaytodo.auth.methods.JwtTokenUtil;
import com.mydaytodo.auth.methods.JwtUserDetailsService;
import com.mydaytodo.auth.methods.model.AuthRequest;
import com.mydaytodo.auth.methods.model.JwtRequest;
import com.mydaytodo.auth.methods.model.JwtResponse;
import com.mydaytodo.auth.methods.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
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
@RequestMapping("/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    // first attempt at a JWT impl class
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/welcome")
    public ResponseEntity<String> greet() throws Exception {
        return new ResponseEntity<>("Welcome to JWT Auth", HttpStatus.OK);
    }
    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return jwtService.generateToken(authRequest.getUsername());
        /*Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request");
        }*/
    }
    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody AuthRequest authRequest) throws Exception {
        String message = String.format("request received to %s user", authRequest.toString());
        // authenticate(authRequest.getUsername(), authRequest.getPassword());

        log.info(message);
        return new ResponseEntity<>(message, HttpStatus.OK);

    }
    @PostMapping("/authenticate")
    public ResponseEntity<?> generateJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        // InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));

    }
    private void authenticate(String username, String password) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException bce) {
            log.info("The Username: {} and password: {} passed are incorrect for {}", username, password, bce.getMessage());
        } catch (AuthenticationException ae) {
            log.info("AuthenticationException");
            ae.printStackTrace();
        }
    }
}
