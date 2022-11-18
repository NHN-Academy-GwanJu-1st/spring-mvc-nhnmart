package com.nhnacademy.springmvc.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Answer {

    long postId;

    String adminId;

    String content;

    LocalDateTime answerDate;

    public static Answer creat(long postId, String adminId, String content) {
        return new Answer(postId, adminId, content);
    }

    private Answer(long postId, String adminId, String content) {
        this.postId = postId;
        this.adminId = adminId;
        this.content = content;
        this.answerDate = LocalDateTime.now();
    }
}
