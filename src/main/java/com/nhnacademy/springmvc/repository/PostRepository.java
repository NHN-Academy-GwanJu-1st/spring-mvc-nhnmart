package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.domain.PostRegisterRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PostRepository {

    // 등록, 조회 , 디테일 뷰,
    boolean exists(long postId);

    boolean isEmpty();

    Map<Long, Post> getAllPosts();

    Post getPost(long postId);

    List<Post> findAllByAccountId(String accountId);

    Post addPost(String accountId, String title, Category category, String content);
}
