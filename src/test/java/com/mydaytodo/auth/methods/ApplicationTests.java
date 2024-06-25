package com.mydaytodo.auth.methods;

import com.mydaytodo.auth.methods.controller.HelloController;
import com.mydaytodo.auth.methods.controller.JwtAuthenticationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private HelloController helloController;

    @Autowired
    private JwtAuthenticationController jwtAuthenticationController;

    @Test
    void smokeTests() {
        Assertions.assertNotNull(helloController);
        Assertions.assertNotNull(jwtAuthenticationController);
    }
}
