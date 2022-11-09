package com.epam.demo.controller;

import com.alibaba.fastjson.JSON;
import com.epam.demo.dto.EmployeeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
class LoginApiControllerTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    private static String token;



    @BeforeEach
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void updateEmployee() throws Exception{
        EmployeeDto registerEmployeeDTO = EmployeeDto.builder()
                .id(Long.parseLong("4"))
                .email("test1@qq.com")
                .password("test")
                .birthday(LocalDate.of(2000,1,1))
                .gender("female")
                .name("test")
                .status("inactive")
                .build();


        mockMvc.perform(MockMvcRequestBuilders
                        .put(new URI("/api/update"))
                        .header("token", token)
                        .content(JSON.toJSONString(registerEmployeeDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void logout() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get(new URI("/api/logout"))
                        .header("token", token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @BeforeEach
    void login() throws Exception{
        EmployeeDto loginUserRequestDTO = EmployeeDto.builder().email("test1@qq.com").password("test").build();

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders
                        .post(new URI("/public/login"))
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .content(JSON.toJSONString(loginUserRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
        token = JSON.parseObject(actions.andReturn().getResponse().getContentAsString()).get("token").toString();
    }
}