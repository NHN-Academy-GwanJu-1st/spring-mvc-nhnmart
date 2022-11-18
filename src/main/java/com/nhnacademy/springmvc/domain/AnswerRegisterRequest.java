package com.nhnacademy.springmvc.domain;

import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class AnswerRegisterRequest {

    @NotNull
    private long postId;

    @NotNull
    private String adminId;

    @NotBlank
    @Max(40000)
    private String content;

}
