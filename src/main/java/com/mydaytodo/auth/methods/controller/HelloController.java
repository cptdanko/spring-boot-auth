package com.mydaytodo.auth.methods.controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
@Data
public class HelloController {

    @RequestMapping("/hello")
    public ResponseEntity<String> sayHello() {
        String msg = "Hello World & JWT";
        log.info("Received {} request and about to respond", msg);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
