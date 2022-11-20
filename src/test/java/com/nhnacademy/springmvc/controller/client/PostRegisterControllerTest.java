package com.nhnacademy.springmvc.controller.client;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.exception.NotAcceptableFileTypeException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindException;
import org.springframework.web.util.NestedServletException;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostRegisterControllerTest {

    private PostRepository postRepository;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new PostRegisterController(postRepository)).build();
    }

    @Test
    void getClientRegisterTest() throws Exception {
        mockMvc.perform(get("/client/register"))
                .andDo(print())
                .andExpect(model().attributeExists("categories"))
                .andExpect(view().name("view/client/register"));
    }

    @Test
    void doClientRegister_noFile_InvalidPost() throws Exception {

        MockMultipartFile emptyFile = new MockMultipartFile(
                "uploadFiles",
                "",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                "".getBytes()
        );

        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(1);
        when(postRepository.addPost(
                post.getAccountId(),
                post.getTitle(),
                post.getCategory(),
                post.getContent())
            ).thenReturn(post);

        mockMvc.perform(multipart("/client/register")
                        .file(emptyFile)
                        .param("accountId", "testId")
                        .param("title", "testTitle")
                        .param("category", "Etc")
                        .param("content", ""))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException() instanceof BindException));
    }

    @Test
    void doClientRegister_noFile_success() throws Exception {

        MockMultipartFile emptyFile = new MockMultipartFile(
                "uploadFiles",
                "",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                "".getBytes()
        );

        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(1);
        when(postRepository.addPost(
                post.getAccountId(),
                post.getTitle(),
                post.getCategory(),
                post.getContent())
        ).thenReturn(post);

        mockMvc.perform(multipart("/client/register")
                        .file(emptyFile)
                        .param("accountId", "testId")
                        .param("title", "testTitle")
                        .param("category", "Etc")
                        .param("content", "testContent"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/client"));
    }

    @Test
    void doClientRegister_existFile_invalidFileType_thenNotAcceptableFileTypeException() throws Exception {

        String UPLOAD_DIR = "/Users/gwanii/Downloads/";

        String filePath = UPLOAD_DIR + "페페_img.png";

        FileInputStream inputStream = new FileInputStream(filePath);

        MockMultipartFile imgFile = new MockMultipartFile(
                "uploadFiles",
                "페페_img.png",
                MediaType.TEXT_XML_VALUE,
                inputStream
        );

        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(1);
        when(postRepository.addPost(
                post.getAccountId(),
                post.getTitle(),
                post.getCategory(),
                post.getContent())
        ).thenReturn(post);

        mockMvc.perform(multipart("/client/register")
                        .file(imgFile)
                        .param("accountId", "testId")
                        .param("title", "testTitle")
                        .param("category", "Etc")
                        .param("content", "testContent"))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotAcceptableFileTypeException));
    }

    @Test
    void doClientRegister_existSingleFile_success() throws Exception {

        String UPLOAD_DIR = "/Users/gwanii/Downloads/";

        String filePath = UPLOAD_DIR + "페페_img.png";

        FileInputStream inputStream = new FileInputStream(filePath);

        MockMultipartFile imgFile = new MockMultipartFile(
                "uploadFiles",
                "페페_img.png",
                MediaType.IMAGE_PNG_VALUE,
                inputStream
        );

        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(1);
        when(postRepository.addPost(
                post.getAccountId(),
                post.getTitle(),
                post.getCategory(),
                post.getContent())
        ).thenReturn(post);

        mockMvc.perform(multipart("/client/register")
                        .file(imgFile)
                        .param("accountId", "testId")
                        .param("title", "testTitle")
                        .param("category", "Etc")
                        .param("content", "testContent"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/client"));

        inputStream.close();
    }

    @Test
    void doClientRegister_existMultiFile_success() throws Exception {

        String UPLOAD_DIR = "/Users/gwanii/Downloads/";

        String filePath = UPLOAD_DIR + "페페_img.png";

        FileInputStream inputStream1 = new FileInputStream(filePath);

        MockMultipartFile imgFile1 = new MockMultipartFile(
                "uploadFiles",
                "페페_img.png",
                MediaType.IMAGE_PNG_VALUE,
                inputStream1
        );

        FileInputStream inputStream2 = new FileInputStream(filePath);

        MockMultipartFile imgFile2 = new MockMultipartFile(
                "uploadFiles",
                "페페_img2.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                inputStream2
        );

        Post post = Post.create("testId", "testTitle", Category.Etc, "testContent");
        post.setId(1);
        when(postRepository.addPost(
                post.getAccountId(),
                post.getTitle(),
                post.getCategory(),
                post.getContent())
        ).thenReturn(post);

        mockMvc.perform(multipart("/client/register")
                        .file(imgFile1)
                        .file(imgFile2)
                        .param("accountId", "testId")
                        .param("title", "testTitle")
                        .param("category", "Etc")
                        .param("content", "testContent"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/client"));

        inputStream1.close();
        inputStream2.close();

    }




}