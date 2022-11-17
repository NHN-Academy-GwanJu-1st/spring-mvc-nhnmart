package com.nhnacademy.springmvc.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDateTime;

@Value
public class PostRegisterRequest {

    @NotNull
    private String accountId;

    @Size(min = 2, max = 200)
    private String title;

    private Category category;

    @Size(max = 40000)
    private String content;

    private String fileName;

    public PostRegisterRequest(String accountId, String title, Category category, String content, String fileName) {
        this.accountId = accountId;
        this.title = title;
        this.category = category;
        this.content = content;
        this.fileName = fileName;
    }
}
