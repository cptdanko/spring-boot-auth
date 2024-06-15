package com.mydaytodo.auth.methods.service;

import com.mydaytodo.auth.methods.repository.UserRepository;
import com.mydaytodo.auth.methods.model.AuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    /*@Autowired
    private PasswordEncoder encoder;*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("In load by username");
        Optional<User> user = repository.getUserList().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
        if(user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User with name %s not found", username));
        }
        log.info("Got the user {}", user.toString());
        UserInfoDetails userInfoDetails = new UserInfoDetails(user.get());
        log.info("Successfully initialised {} userdetails object", userInfoDetails.toString());
        return userInfoDetails;
    }

    public User addUser(AuthRequest authRequest) {
        log.info("got request to add user, {}", authRequest.toString());
        authRequest.setPassword(new BCryptPasswordEncoder().encode(authRequest.getPassword()));
        log.info("object with encoded password,[ {} ]", authRequest.toString());
        User user = new User(authRequest.getUsername(), authRequest.getPassword(), new ArrayList<>());
        repository.getUserList().add(user);
        log.info("no of users are,[ {} ]", repository.getUserList().size());
        return user;
    }
}
