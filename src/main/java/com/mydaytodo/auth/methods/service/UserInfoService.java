package com.mydaytodo.auth.methods.service;

import com.mydaytodo.auth.methods.UserRepository;
import com.mydaytodo.auth.methods.model.AuthRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    /*@Autowired
    private PasswordEncoder encoder;*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getUserList().stream().findAny().get();
        UserInfoDetails userInfoDetails = new UserInfoDetails(user);
        log.info("Successfully initialised {} userdetails object", userInfoDetails.toString());
        return userInfoDetails;
    }

    public String addUser(AuthRequest authRequest) {

        authRequest.setPassword(new BCryptPasswordEncoder().encode(authRequest.getPassword()));
        User user = new User(authRequest.getUsername(), authRequest.getPassword(), new ArrayList<>());
        repository.getUserList().add(user);
        return "User Added Successfully";
    }
}
