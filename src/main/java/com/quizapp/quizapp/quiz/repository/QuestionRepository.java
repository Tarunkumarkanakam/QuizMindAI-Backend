package com.quizapp.quizapp.quiz.repository;

import com.quizapp.quizapp.quiz.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findByQuestionId(String questionId);

    @Query("SELECT e FROM Question e WHERE e.id = max(e.id) ")
    Optional<Question> findLastId();

    @Query("SELECT e FROM Question e WHERE e.exam.examId = :id ")
    List<Question> findByExamId(String id);

    @Query("SELECT distinct e.topic FROM Question e WHERE e.exam.examId = :examId and e.lockStatus=false ")
    List<String> findAllTopicsByExamId(String examId);

    @Query("SELECT e FROM Question e WHERE e.exam.examId = :id and e.topic = :topic and e.lockStatus=false order by e.id")
    List<Question> findByExamIdAndTopic(@Param("id") String id, @Param("topic") String topic);



}