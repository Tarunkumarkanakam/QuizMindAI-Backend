package com.quizapp.quizapp.quiz.repository;

import com.quizapp.quizapp.quiz.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
