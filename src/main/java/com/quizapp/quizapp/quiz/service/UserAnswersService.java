package com.quizapp.quizapp.quiz.service;

import com.quizapp.quizapp.core.model.User;
import com.quizapp.quizapp.core.repository.UserRepository;
import com.quizapp.quizapp.quiz.dto.UserAnswerResponse;
import com.quizapp.quizapp.quiz.model.UserAnswers;
import com.quizapp.quizapp.quiz.repository.AnswerRepository;
import com.quizapp.quizapp.quiz.repository.ExamRepository;
import com.quizapp.quizapp.quiz.repository.QuestionRepository;
import com.quizapp.quizapp.quiz.repository.UserAnswersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserAnswersService {

    private final UserAnswersRepository userAnswersRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void saveOrUpdateUserAnswer(String email, String examId, Integer questionId, Integer answerId) {
        Optional<UserAnswers> optionalUserAnswer = userAnswersRepository.findByUserEmailAndExamIdAndQuestionId(email, examId, questionId);

        UserAnswers userAnswer;
        if (optionalUserAnswer.isPresent()) {
            userAnswer = optionalUserAnswer.get();
            userAnswer.setAnswer(answerRepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("Invalid answerId")));
        } else {
            userAnswer = new UserAnswers();
            userAnswer.setUser(userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Invalid email")));
            userAnswer.setExam(examRepository.findByExamId(examId).orElseThrow(() -> new IllegalArgumentException("Invalid examId")));
            userAnswer.setQuestion(questionRepository.findById(questionId).orElseThrow(() -> new IllegalArgumentException("Invalid questionId")));
            userAnswer.setAnswer(answerRepository.findById(answerId).orElseThrow(() -> new IllegalArgumentException("Invalid answerId")));
            userAnswer.setAnsweredAt(LocalDateTime.now());
        }

        userAnswersRepository.save(userAnswer);
    }

    public List<UserAnswerResponse> getUserAnswers(String examId, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Invalid email");
        }


        List<UserAnswers> userAnswers = userAnswersRepository.findByExamExamIdAndUserId(examId, user.get().getId());

        return userAnswers.stream()
                .map(answer -> new UserAnswerResponse(answer.getQuestion().getId(), answer.getAnswer().getId().toString()))
                .collect(Collectors.toList());
    }

}
