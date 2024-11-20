package com.quizapp.quizapp.quiz.repository;

import com.quizapp.quizapp.quiz.model.Question;
import com.quizapp.quizapp.quiz.model.Results;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultsRepository extends JpaRepository<Results, Integer> {
}
