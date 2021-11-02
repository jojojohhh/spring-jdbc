package com.spring.jdbc.controller;

import com.spring.jdbc.IntegrationTest;
import com.spring.jdbc.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends IntegrationTest {

    private String userAccount = "test";
    private String userPassword = "1234";
    private String userName = "테스트";
    private String userAddress = "서울시 연희동";
    private String userPhoneNo = "01012345678";
    private String userEmail = "test@test.test";
    private User.UserRole userRole = User.UserRole.ROLE_USER;
    private User user;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
        user = User.builder()
                .account(userAccount)
                .password(userPassword)
                .name(userName)
                .address(userAddress)
                .phoneNo(userPhoneNo)
                .email(userEmail)
                .role(userRole)
                .build();
    }

    @Test
    public void 유저생성() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8"))
                .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("account").value(userAccount))
                .andExpect(jsonPath("password").value(userPassword))
                .andExpect(jsonPath("name").value(userName))
                .andExpect(jsonPath("address").value(userAddress))
                .andExpect(jsonPath("phoneNo").value(userPhoneNo))
                .andExpect(jsonPath("email").value(userEmail))
                .andExpect(jsonPath("role").value(userRole.toString()));
    }

    @Test
    public void 모든유저조회() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());

        resultActions
                .andExpect(status().isOk());
    }

    @Test
    public void 유저조회() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/user/{id}", 1)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                ;
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("account").value("josangjea"));
    }
}
