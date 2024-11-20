package com.quizapp.quizapp.quiz.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamDataResponse {
    private TopicDTO questionDTO;
    private QuestionAnswersDTO answersDTOList;
}
