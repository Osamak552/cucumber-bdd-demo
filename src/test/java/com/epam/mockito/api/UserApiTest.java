package com.epam.mockito.api;

import com.epam.StaticData;
import com.epam.controllers.UserApis;
import com.epam.dto.UserRequest;
import com.epam.entities.Role;
import com.epam.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApis.class)
public class UserApiTest {
    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;

    /**
     *
     * @Post requests
     */
    @Test
    void validCreateUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .userId(1)
                .firstName("osama")
                .lastName("khan")
                .username("osama@admin")
                .password("12345")
                .role(Role.ADMIN)
                .email("osama@gmail.com")
                .build();
        String json = StaticData.mapToJson(userRequest);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    void invalidCreateUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .userId(1)
                .firstName(" ")
                .lastName("khan")
                .username("osama@admin")
                .password("12345")
                .role(Role.ADMIN)
                .email("osama@gmail.com")
                .build();
        String json = StaticData.mapToJson(userRequest);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    /**
     * @Get requests
     */

    @Test
    void getAllUsers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    void validGetUserById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    void validGetUserByUsername() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/users/username/osama@admin")
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    void validUpdateUser() throws Exception {
        UserRequest userRequest = UserRequest.builder()
                .userId(1)
                .firstName("osama")
                .lastName("khan")
                .username("osama@admin")
                .password("12345")
                .role(Role.ADMIN)
                .email("osama@gmail.com")
                .build();
        String json = StaticData.mapToJson(userRequest);
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    void validDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent());
    }

}
