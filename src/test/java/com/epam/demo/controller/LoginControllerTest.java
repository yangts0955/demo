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
import java.util.Random;

@ExtendWith(SpringExtension.class)
//SpringBoot1.4版本之前用的是@SpringApplicationConfiguration(classes = Application.class)
@SpringBootTest
//测试环境使用，用来表示测试环境使用的ApplicationContext将是WebApplicationContext类型的
@WebAppConfiguration
@AutoConfigureMockMvc
class LoginControllerTest {
    private static String token;

    @Autowired
    WebApplicationContext context;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void register() throws Exception{
        Random random = new Random();
        Integer random_num = random.nextInt(200,400);
        System.out.println(random_num);
        EmployeeDto registerEmployeeDTO = EmployeeDto.builder()
                .email("test"+ random_num +"@qq.com")
                .password("test")
                .birthday(LocalDate.of(2000,1,1))
                .gender("male")
                .name("test")
                .build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(new URI("/public/register"))
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .content(JSON.toJSONString(registerEmployeeDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
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

    @Test
    void login_incorrect_password() throws Exception{
        EmployeeDto loginUserRequestDTO = EmployeeDto.builder().email("test1@qq.com").password("tes").build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(new URI("/public/login"))
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .content(JSON.toJSONString(loginUserRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void login_email_not_found() throws Exception{
        EmployeeDto loginUserRequestDTO = EmployeeDto.builder().email("test9999@qq.com").password("test").build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post(new URI("/public/login"))
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                        .content(JSON.toJSONString(loginUserRequestDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

}