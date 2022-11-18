package com.nhnacademy.springmvc.controller.admin;

import com.nhnacademy.springmvc.domain.Answer;
import com.nhnacademy.springmvc.domain.AnswerRegisterRequest;
import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.exception.PostNotFoundException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.AnswerRepository;
import com.nhnacademy.springmvc.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PostRepository postRepository;
    private final AnswerRepository answerRepository;

    public AdminController(PostRepository postRepository, AnswerRepository answerRepository) {
        this.postRepository = postRepository;
        this.answerRepository = answerRepository;
    }

    @GetMapping
    public String adminHome(Model model) {

        model.addAttribute("posts", postRepository.findAllByAnswerStatusIsFalse());
        model.addAttribute("categories", Category.values());

        return "view/admin/index";
    }

    @GetMapping("/detail/{postId}")
    public String getDetailPost(@PathVariable(value = "postId") long postId, Model model) {

        if (!postRepository.exists(postId)) {
            throw new PostNotFoundException();
        }

        model.addAttribute("post", postRepository.getPost(postId));

        return "view/admin/detail";
    }

    @PostMapping("/answer/{postId}")
    public String doAnswer(@PathVariable(value = "postId") long postId,
                           @Valid @ModelAttribute(value = "answer") AnswerRegisterRequest answerRequest,
                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Answer answer = answerRepository.registerAnswer(
                answerRequest.getPostId(),
                answerRequest.getAdminId(),
                answerRequest.getContent()
        );

        Post post = postRepository.getPost(postId);
        post.setAnswer(answer);
        post.setAnswerStatus(true);

        return "redirect:/admin";
    }

    @GetMapping(params = {"category"})
    public String searchCategoryPost(@RequestParam(value = "category", required = false) Category category, Model model) {

        if (Objects.isNull(category)) {
            return "redirect:/admin";
        }

        model.addAttribute("posts", postRepository.findAllByCategory(category));
        model.addAttribute("categories", Category.values());

        return "view/admin/index";
    }
}
