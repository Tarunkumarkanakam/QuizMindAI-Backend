package com.quizapp.quizapp.quiz.repository;

import com.quizapp.quizapp.quiz.model.Exam;
import com.quizapp.quizapp.quiz.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT s FROM Session s WHERE s.user.email = :email ")
    List<Session> findExamsByUserId(String email);

    @Query("SELECT DISTINCT s.sessionId FROM Session s WHERE s.createdBy.email = :email ")
    List<String> findSessionByLogUserId(String email);

    @Query("SELECT s FROM Session s WHERE s.user.email = :email AND s.exam.examId=:examId AND CURRENT_TIMESTAMP BETWEEN s.examStart AND s.examEnd order by s.createdDate ")
    Session findActiveSessionByUserIdAndExamExamId(String email, String examId);

    @Query("SELECT s FROM Session s WHERE s.createdBy.email = :email and s.sessionId = :sessionId order by s.createdDate ")
    List<Session> findAllSessionsBySessionId(String email, String sessionId);
}
