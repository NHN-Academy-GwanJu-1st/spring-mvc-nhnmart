package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;

import java.util.List;

public interface PostRepository {

    boolean exists(long postId);

    boolean isEmpty();

    List<Post> findAllByAnswerStatusIsFalse();

    Post getPost(long postId);

    List<Post> findAllByAccountId(String accountId);

    Post addPost(String accountId, String title, Category category, String content);

    List<Post> findAllByCategory(Category category);
}
