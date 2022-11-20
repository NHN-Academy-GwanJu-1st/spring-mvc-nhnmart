package com.nhnacademy.springmvc.controller.admin;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.exception.PostNotFoundException;
import com.nhnacademy.springmvc.repository.AnswerRepository;
import com.nhnacademy.springmvc.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest {

    private PostRepository postRepository;

    private AnswerRepository answerRepository;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        answerRepository = mock(AnswerRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AdminController(postRepository, answerRepository)).build();
    }

    @Test
    void adminHomeTest() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attribute("categories", Category.values()))
                .andExpect(view().name("view/admin/index"));
    }

    @Test
    void getDetailPost_notExistPost_thenPostNotFoundException() throws Exception {

        long postId = 1234;

        mockMvc.perform(get("/admin/detail/{postId}", postId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PostNotFoundException));

    }

    @Test
    void getDetailPost_existPost() throws Exception {

        long postId = 1;

        mockMvc.perform(get("/client/detail/{postId}", postId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PostNotFoundException));

    }

}