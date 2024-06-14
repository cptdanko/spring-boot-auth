package com.mydaytodo.auth.methods;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private String defaultUsername = "qin@gmail.com";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(defaultUsername.equals(username)) {
            return new User(username, "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException(String.format("Username %s not found", username));
        }
    }
}
