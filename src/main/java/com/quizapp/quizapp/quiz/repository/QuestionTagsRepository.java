package com.quizapp.quizapp.quiz.repository;

import com.quizapp.quizapp.quiz.model.QuestionTags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagsRepository extends JpaRepository<QuestionTags, Integer> {
}
