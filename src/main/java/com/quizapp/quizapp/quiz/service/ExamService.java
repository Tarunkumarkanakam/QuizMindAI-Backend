package com.quizapp.quizapp.quiz.service;

import com.quizapp.quizapp.quiz.dto.*;
import com.quizapp.quizapp.quiz.model.Answer;
import com.quizapp.quizapp.quiz.model.Exam;
import com.quizapp.quizapp.quiz.model.Question;
import com.quizapp.quizapp.quiz.repository.ExamRepository;
import com.quizapp.quizapp.quiz.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void createExam(@RequestBody @Valid CreateExamRequest request) {
        var exam = Exam.builder()
                .examId(request.getExamId())
                .examDescription(request.getExamDescription())
                .examName(request.getExamName())
                .build();
        examRepository.save(exam);
    }

    public Map<String, List<QuestionAnswersDTO>> findQuestionsByExamId(String examId) {
        Map<String, List<QuestionAnswersDTO>> response = new HashMap<>();

        try {
            var topics = questionRepository.findAllTopicsByExamId(examId);

            for (String topic : topics) {
                var data = questionRepository.findByExamIdAndTopic(examId, topic);
                List<QuestionAnswersDTO> questionAnswersDTOList = data.stream()
                        .map(this::convertToQuestionAnswersDTO)
                        .collect(Collectors.toList());

                Collections.shuffle(questionAnswersDTOList);
                response.put(topic, questionAnswersDTOList);
            }
        } catch (Exception e) {
            log.error("Error finding questions by exam ID: {}", examId, e);
        }

        return response;
    }

    private QuestionAnswersDTO convertToQuestionAnswersDTO(Question question) {
        LinkedHashMap<Integer, String> answerMap = question.getAnswers().stream()
                .collect(Collectors.toMap(
                        Answer::getId,
                        Answer::getAnswerText,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        return QuestionAnswersDTO.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .answer(answerMap)
                .build();
    }
}
