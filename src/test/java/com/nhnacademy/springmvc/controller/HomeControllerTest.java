package com.nhnacademy.springmvc.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


class HomeControllerTest {

    private MockMvc mockMvc;
    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new HomeController()).build();
        session = new MockHttpSession();
    }

    @Test
    void homeController_noSession() throws Exception {
        mockMvc.perform(get("/")
                        .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("view/index"));
    }

    @Test
    void loginSession_isAdmin() throws Exception {
        session.setAttribute("admin", "adminId");

        mockMvc.perform(get("/")
                        .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("view/admin/index"));
    }

    @Test
    void loginSession_isClient() throws Exception {
        session.setAttribute("client", "clientId");

        mockMvc.perform(get("/")
                        .session(session))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("view/client/index"));
    }



}