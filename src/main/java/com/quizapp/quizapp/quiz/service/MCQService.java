package com.quizapp.quizapp.quiz.service;

import com.quizapp.quizapp.quiz.dto.AnswerDTO;
import com.quizapp.quizapp.quiz.dto.QuestionDTO;
import com.quizapp.quizapp.quiz.model.Exam;
import com.quizapp.quizapp.quiz.model.Question;
import com.quizapp.quizapp.quiz.model.Answer;
import com.quizapp.quizapp.quiz.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MCQService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ExamRepository examRepository;

    public void saveQuestions(String examId, List<QuestionDTO> questionDTOs) {
        Exam exam = examRepository.findByExamId(examId)
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        for (QuestionDTO questionDTO : questionDTOs) {
            // Create Question entity
            Question question = new Question();
            question.setQuestionId(UUID.randomUUID().toString());
            question.setQuestion(questionDTO.getQuestionText());
            question.setQuestionType(questionDTO.getQuestionType() != null ? questionDTO.getQuestionType() : "MCQ");
            question.setTopic(questionDTO.getTopic());
            question.setExam(exam);
            question.setCreatedDate(LocalDateTime.now());
            question.setLockStatus(false);

            // Save question
            question = questionRepository.save(question);
            List<String> options = questionDTO.getOptions();
            var correctOption = questionDTO.getCorrectOptionIndex();
            // Save answers
            for (int i=0; i<options.size(); i++) {
                Answer answer = new Answer();
                answer.setAnswerId(UUID.randomUUID().toString());
                answer.setQuestion(question);
                answer.setAnswerText(options.get(i));
                answer.setIsCorrect(i==correctOption);
                answer.setExplanation(questionDTO.getExplanation());
                answer.setCreatedDate(LocalDateTime.now());
                answerRepository.save(answer);
            }
        }
    }
}
