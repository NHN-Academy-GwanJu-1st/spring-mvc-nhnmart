package com.nhnacademy.springmvc.controller.admin;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.exception.AlreadyAnswerExistException;
import com.nhnacademy.springmvc.exception.PostNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.AnswerRepository;
import com.nhnacademy.springmvc.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void getDetailPost_existPost_butAlreadyAnswer_thenAlreadyAnswerExistException() throws Exception {

        long postId = 1;
        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(postId);
        post.setAnswerStatus(true);
        when(postRepository.exists(postId)).thenReturn(true);
        when(postRepository.getPost(postId)).thenReturn(post);

        mockMvc.perform(get("/admin/detail/{postId}", postId))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AlreadyAnswerExistException));

    }

    @Test
    void getDetailPost_success() throws Exception {
        long postId = 1;
        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(postId);
        when(postRepository.exists(postId)).thenReturn(true);
        when(postRepository.getPost(postId)).thenReturn(post);

        mockMvc.perform(get("/admin/detail/{postId}", postId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("post", post))
                .andExpect(view().name("view/admin/detail"));
    }

    @Test
    void doAnswer_invalidAnswer() throws Exception {
        long postId = 1;

        Throwable th = catchThrowable(() ->
                mockMvc.perform(post("/admin/answer/{postId}", postId)
                                .param("postId", String.valueOf(postId))
                                .param("adminId", "adminId")
                                .param("content", ""))
                        .andDo(print()));


        assertThat(th).isInstanceOf(NestedServletException.class)
                .hasCauseInstanceOf(ValidationFailedException.class);

    }

    @Test
    void doAnswer_success() throws Exception {

        long postId = 1;

        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(postId);

        when(postRepository.getPost(postId)).thenReturn(post);

        mockMvc.perform(post("/admin/answer")
                        .param("postId", String.valueOf(postId))
                        .param("adminId", "testId")
                        .param("content", "testContent"))
                .andDo(print());
    }

    @Test
    void searchCategoryPost_paramIsNull() throws Exception {

        mockMvc.perform(get("/admin")
                        .param("category", ""))
                .andDo(print())
                .andExpect(view().name("redirect:/admin"));
    }

    @Test
    void searchCategoryPost_paramIsNotNull() throws Exception {

        mockMvc.perform(get("/admin")
                        .param("category", "Dissatisfaction"))
                .andDo(print())
                .andExpect(model().attributeExists("posts"))
                .andExpect(model().attributeExists("categories"))
                .andExpect(view().name("view/admin/index"));
    }





}