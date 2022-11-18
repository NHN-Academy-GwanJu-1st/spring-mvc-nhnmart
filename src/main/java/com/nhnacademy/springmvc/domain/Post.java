package com.nhnacademy.springmvc.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Post {

    @Setter
    private long id;
    private String accountId;
    private String title;
    private Category category;
    private String content;
    private LocalDateTime registerDate;

    @Setter
    private List<String> fileList;

    @Setter
    private boolean answerStatus;

    @Setter
    private Answer answer;


    public static Post create(String accountId, String title, Category category, String content) {
        return new Post(accountId, title, category, content);
    }

    private Post(String accountId, String title, Category category, String content) {
        this.accountId = accountId;
        this.title = title;
        this.category = category;
        this.content = content;
        this.registerDate = LocalDateTime.now();
        this.fileList = new ArrayList<>();
        this.answerStatus = false;
        this.answer = null;
    }

    public boolean isAnswerStatus() {
        return this.answerStatus;
    }

}
