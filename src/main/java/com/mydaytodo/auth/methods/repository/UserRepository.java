package com.mydaytodo.auth.methods.repository;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * To be a hard coded list for now
 */
@Component
@Data
@Slf4j
@ToString
public class UserRepository {

    private List<User> userList = new ArrayList<>();
    private final String defaultUsername = "bhuman.soni@gmail.comm ";

    @PostConstruct
    public void addDefaultUser() {
        User userDetails = new User(defaultUsername, new BCryptPasswordEncoder().encode("password"), new ArrayList<>());
        log.info("Adding default user [ {} ] with details [ {} ] ", userDetails.getUsername(), userDetails.toString());
        userList.add(userDetails);
    }
}
