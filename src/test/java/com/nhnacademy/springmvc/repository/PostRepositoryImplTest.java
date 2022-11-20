package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryImplTest {

    private PostRepository postRepository;
    private Post testPost;

    @BeforeEach
    void setUp() {
        postRepository = new PostRepositoryImpl();
        testPost = Post.create("testId", "testTitle", Category.Dissatisfaction, "testContent");

    }

    @Test
    void post_isEmpty_isTrue() {
        assertThat(postRepository.isEmpty()).isTrue();
    }

    @Test
    void post_isNotEmpty_isFalse() {
        postRepository.addPost(
                testPost.getAccountId(),
                testPost.getTitle(),
                testPost.getCategory(),
                testPost.getContent()
        );

        assertThat(postRepository.isEmpty()).isFalse();
    }

    @Test
    void existPost_isTrue() {
        postRepository.addPost(
                testPost.getAccountId(),
                testPost.getTitle(),
                testPost.getCategory(),
                testPost.getContent()
        );
        assertThat(postRepository.exists(1)).isTrue();
    }

    @Test
    void notExistPost_isFalse() {
        assertThat(postRepository.exists(1234)).isFalse();
    }

    @Test
    void findAllByAnswerStatusIsFalseTest() {
        postRepository.addPost(
                testPost.getAccountId(),
                testPost.getTitle(),
                testPost.getCategory(),
                testPost.getContent()
        );
        List<Post> postList = postRepository.findAllByAnswerStatusIsFalse();
        assertThat(postList.size()).isEqualTo(1);
    }

    @Test
    void posts_statusIsTrue_findAllByAnswerStatusIsFalse() {
        Post post = postRepository.addPost(
                testPost.getAccountId(),
                testPost.getTitle(),
                testPost.getCategory(),
                testPost.getContent()
        );
        post.setAnswerStatus(true);

        List<Post> postList = postRepository.findAllByAnswerStatusIsFalse();
        assertThat(postList.size()).isEqualTo(0);
        assertThat(postList.isEmpty()).isTrue();

    }

    @Test
    void existPost_getPostTest() {
        postRepository.addPost(
                testPost.getAccountId(),
                testPost.getTitle(),
                testPost.getCategory(),
                testPost.getContent()
        );

        Post post = postRepository.getPost(1);
        assertThat(post).isNotNull();
        assertThat(post.getAccountId()).isEqualTo(testPost.getAccountId());
        assertThat(post.getTitle()).isEqualTo(testPost.getTitle());
        assertThat(post.getContent()).isEqualTo(testPost.getContent());
        assertThat(post.getCategory()).isEqualTo(testPost.getCategory());
    }

    @Test
    void findAllByAccountIdTest() {
        for (int i = 0; i < 10; i++) {
            postRepository.addPost(
                    testPost.getAccountId(),
                    testPost.getTitle(),
                    testPost.getCategory(),
                    testPost.getContent()
            );
        }

        List<Post> postList = postRepository.findAllByAccountId(testPost.getAccountId());

        postRepository.addPost(
                "test2",
                testPost.getTitle(),
                testPost.getCategory(),
                testPost.getContent()
        );

        assertThat(postList.size()).isEqualTo(10);
        assertThat(postRepository.findAllByAccountId("test2").size()).isEqualTo(1);
    }

    @Test
    void findAllByCategoryTest() {
        for (int i = 0; i < 10; i++) {
            postRepository.addPost(
                    testPost.getAccountId(),
                    testPost.getTitle(),
                    testPost.getCategory(),
                    testPost.getContent()
            );
        }

        postRepository.addPost(
                testPost.getAccountId(),
                testPost.getTitle(),
                Category.Etc,
                testPost.getContent()
        );

        assertThat(postRepository.findAllByCategory(testPost.getCategory()).size()).isEqualTo(10);
    }



}
