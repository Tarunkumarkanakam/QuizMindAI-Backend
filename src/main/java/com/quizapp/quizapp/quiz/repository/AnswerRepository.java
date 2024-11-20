package com.quizapp.quizapp.quiz.repository;

import com.quizapp.quizapp.quiz.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Optional<Answer> findByAnswerId(String id);
}
