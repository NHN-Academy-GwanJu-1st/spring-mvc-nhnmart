package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Account;
import com.nhnacademy.springmvc.domain.Role;
import com.nhnacademy.springmvc.exception.AccountNotFoundException;
import com.nhnacademy.springmvc.repository.AccountRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class LoginControllerTest {

    private AccountRepository accountRepository;

    private MockMvc mockMvc;

    private Account testAccountAdmin;
    private Account testAccountClient;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new LoginController(accountRepository)).build();
        testAccountAdmin = new Account("testId", "testPw", "tester", Role.Admin);
        testAccountClient = new Account("testId", "testPw", "tester", Role.Client);

    }

    @Test
    void doLogin_notExistAccount_thenAccountNotFoundException() throws Exception {

        when(accountRepository.matches("noExistId", "noExistPw")).thenReturn(false);

        mockMvc.perform(post("/login")
                        .param("id", "noExistId")
                        .param("password", "noExistPw"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AccountNotFoundException));
    }

    @Test
    void doLogin_existAccount_isAdmin() throws Exception {

        when(accountRepository.matches("testId", "testPw")).thenReturn(true);
        when(accountRepository.getAccount("testId")).thenReturn(testAccountAdmin);

        mockMvc.perform(post("/login")
                        .param("id", "testId")
                        .param("password", "testPw"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    void doLogin_existAccount_isClient() throws Exception {

        when(accountRepository.matches("testId", "testPw")).thenReturn(true);
        when(accountRepository.getAccount("testId")).thenReturn(testAccountClient);

        mockMvc.perform(post("/login")
                        .param("id", "testId")
                        .param("password", "testPw"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/client"));
    }


}