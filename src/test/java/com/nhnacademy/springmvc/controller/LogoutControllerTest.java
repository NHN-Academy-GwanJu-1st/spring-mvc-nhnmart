package com.nhnacademy.springmvc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class LogoutControllerTest {

    private MockMvc mockMvc;
    MockHttpSession session;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LogoutController()).build();
        session = new MockHttpSession();
    }

    @Test
    void doLogout_adminSession() throws Exception {

        session.setAttribute("admin", "adminId");
        mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getRequest().getSession().getAttribute("admin")).isNull())
                .andExpect(view().name("view/index"));
    }

    @Test
    void doLogout_clientSession() throws Exception {

        session.setAttribute("client", "clientId");
        mockMvc.perform(get("/logout"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getRequest().getSession().getAttribute("client")).isNull())
                .andExpect(view().name("view/index"));
    }


}