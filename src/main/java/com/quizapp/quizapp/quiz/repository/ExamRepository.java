package com.quizapp.quizapp.quiz.repository;

import com.quizapp.quizapp.quiz.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
    Optional<Exam> findByExamId(String id);
}
