package com.mydaytodo.auth.methods;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Nested
@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void welcomeEndpointTest() throws Exception {
        String expectedResponse = "Welcome to JWT Auth";
        mockMvc.perform(get("/api/auth/welcome")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content()
                        .string(containsString(expectedResponse)));

    }

    @Test
    @WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
    void helloEndpointTest() throws Exception {

        String expectedResponse = "Hello World & JWT";
        mockMvc.perform(get("/api/hello")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content()
                        .string(containsString(expectedResponse)));
    }
}
