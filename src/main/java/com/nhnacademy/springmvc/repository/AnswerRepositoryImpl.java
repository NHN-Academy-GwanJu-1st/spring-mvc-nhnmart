package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Answer;
import java.util.HashMap;
import java.util.Map;

public class AnswerRepositoryImpl implements AnswerRepository {

    private final Map<Long, Answer> answers = new HashMap<>();

    @Override
    public Answer registerAnswer(long postId, String adminId, String content) {
        Answer answer = Answer.creat(postId, adminId, content);
        answers.put(postId, answer);
        return answer;
    }
}
