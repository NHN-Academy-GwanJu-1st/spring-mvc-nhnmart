package com.nhnacademy.springmvc.domain;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public class AnswerRegisterRequest {

    @NotNull
    private long postId;

    @NotNull
    private String adminId;

    @NotBlank
    @Size(max = 40000)
    private String content;

}
