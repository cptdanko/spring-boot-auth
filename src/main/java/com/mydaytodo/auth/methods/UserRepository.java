package com.mydaytodo.auth.methods;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
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
    private final String defaultUsername = "cptdanko";

    @PostConstruct
    public void addSomeUsers() {
        User userDetails = new User(defaultUsername, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", new ArrayList<>());
        log.info("The user details for {} are {}", userDetails.getUsername(), userDetails.toString());
        userList.add(userDetails);
    }


}
