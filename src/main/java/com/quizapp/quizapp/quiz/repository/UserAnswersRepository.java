package com.quizapp.quizapp.quiz.repository;

import com.quizapp.quizapp.quiz.model.UserAnswers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserAnswersRepository extends JpaRepository<UserAnswers, Integer> {

    @Query("SELECT ua FROM UserAnswers ua WHERE ua.user.email = :email AND ua.exam.examId = :examId AND ua.question.id = :questionId ")
    Optional<UserAnswers> findByUserEmailAndExamIdAndQuestionId(
            @Param("email") String email,
            @Param("examId") String examId,
            @Param("questionId") Integer questionId);

    List<UserAnswers> findByExamExamIdAndUserId(String examId, Integer userId);

}
