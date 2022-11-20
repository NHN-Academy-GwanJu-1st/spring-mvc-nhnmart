package com.nhnacademy.springmvc.repository;

import com.nhnacademy.springmvc.domain.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class AnswerRepositoryImplTest {

    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        answerRepository = new AnswerRepositoryImpl();
    }

    @Test
    void registerAnswerTest() {
        Answer answer = answerRepository.registerAnswer(Long.parseLong("1"), "testId", "testContent");

        assertThat(answer).isNotNull();
        assertThat(answer.getPostId()).isEqualTo(1);
        assertThat(answer.getAdminId()).isEqualTo("testId");
        assertThat(answer.getContent()).isEqualTo("testContent");
    }

}