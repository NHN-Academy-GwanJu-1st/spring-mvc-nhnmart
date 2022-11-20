package com.nhnacademy.springmvc.controller.client;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.exception.PostNotFoundException;
import com.nhnacademy.springmvc.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest {

    private PostRepository postRepository;

    private MockMvc mockMvc;

    MockHttpSession session;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PostController(postRepository)).build();
        session = new MockHttpSession();
        session.setAttribute("client", "clientId");
    }

    @Test
    void clientHome_postIsEmpty() throws Exception {

        when(postRepository.isEmpty()).thenReturn(true);

        mockMvc.perform(get("/client"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("posts"))
                .andExpect(view().name("view/client/index"));
    }

    @Test
    void clientHome_postIsNotEmpty() throws Exception {

        when(postRepository.isEmpty()).thenReturn(false);

        mockMvc.perform(get("/client"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("posts"))
                .andExpect(view().name("view/client/index"));
    }

    @Test
    void postDetail_notExistPost_thenPostNotFoundException() throws Exception {

        long postId = 1234;

        mockMvc.perform(get("/client/detail/{postId}", postId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PostNotFoundException));
    }

    @Test
    void postDetail_existPost() throws Exception {
        long postId = 1;

        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(postId);

        when(postRepository.exists(postId)).thenReturn(true);
        when(postRepository.getPost(postId)).thenReturn(post);

        mockMvc.perform(get("/client/detail/{postId}", postId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("post"))
                .andExpect(model().attribute("post", post))
                .andExpect(view().name("view/client/detail"));

    }
}