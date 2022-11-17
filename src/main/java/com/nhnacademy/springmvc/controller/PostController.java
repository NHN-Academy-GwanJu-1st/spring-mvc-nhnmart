package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/client")
public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public String clientHome(Model model) {
        log.info("PostController clientHome");
        if (!postRepository.isEmpty()) {
            model.addAttribute("posts", postRepository.getAllPosts());
        }

        return "view/client/index";
    }




}
