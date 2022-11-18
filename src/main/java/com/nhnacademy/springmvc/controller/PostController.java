package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.exception.PostNotFoundException;
import com.nhnacademy.springmvc.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/client")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public String clientHome(HttpServletRequest request, Model model) {
        log.info("PostController clientHome");
        HttpSession session = request.getSession();
        String accountId = (String) session.getAttribute("client");

        if (!postRepository.isEmpty()) {
            model.addAttribute("posts", postRepository.findAllByAccountId(accountId));
        }

        return "view/client/index";
    }

    @GetMapping("/detail/{postId}")
    public String postDetail(@PathVariable(value = "postId") long postId, Model model) {

        if (!postRepository.exists(postId)) {
            throw new PostNotFoundException();
        }

        Post post = postRepository.getPost(postId);
        model.addAttribute("post", post);

        return "view/client/detail";
    }
}
