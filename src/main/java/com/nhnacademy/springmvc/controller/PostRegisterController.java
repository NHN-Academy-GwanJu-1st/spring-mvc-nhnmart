package com.nhnacademy.springmvc.controller;

import com.nhnacademy.springmvc.domain.Category;
import com.nhnacademy.springmvc.domain.Post;
import com.nhnacademy.springmvc.domain.PostRegisterRequest;
import com.nhnacademy.springmvc.exception.NotAcceptableFileTypeException;
import com.nhnacademy.springmvc.exception.ValidationFailedException;
import com.nhnacademy.springmvc.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/client/register")
public class PostRegisterController {

    private static String UPLOAD_DIR = "/Users/gwanii/Downloads/";
    private static List<String> acceptableFileType =  List.of("image/gif","image/jpg","image/jpeg","image/png");

    private final PostRepository postRepository;

    public PostRegisterController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping
    public String getClientRegister(Model model) {
        model.addAttribute("categories", Category.values());
        return "view/client/register";
    }

    @PostMapping
    public String doClientRegister(@Valid @ModelAttribute(value = "post") PostRegisterRequest postRequest,
                                   @RequestParam(value = "uploadFiles", required = false) MultipartFile[] uploadFiles,
                                   Model model,
                                   BindingResult bindingResult) throws IOException {

        log.info("PostRegisterController doClientRegister");

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        List<String> fileList = new ArrayList<>();

        for (MultipartFile file : uploadFiles) {
            fileTypeCheck(file);
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            fileList.add(fileName);
            file.transferTo(Paths.get(UPLOAD_DIR + fileName));
        }

        Post post = postRepository.addPost(
                postRequest.getAccountId(),
                postRequest.getTitle(),
                postRequest.getCategory(),
                postRequest.getContent()
        );
        post.setFileList(fileList);

        model.addAttribute("post", post);

        return "view/client/detail";
    }

    private void fileTypeCheck(MultipartFile file) {
        if (!acceptableFileType.contains(file.getContentType())) {
            throw new NotAcceptableFileTypeException();
        }
    }

}
