package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Answer;

public interface AnswerRepository {

    Answer registerAnswer(long postId, String adminId, String content);
}
