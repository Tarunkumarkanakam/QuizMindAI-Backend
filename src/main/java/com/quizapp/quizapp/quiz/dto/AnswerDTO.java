package com.quizapp.quizapp.quiz.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDTO {
    private String answerText;
    private Boolean isCorrect;
    private String explanation;
}
