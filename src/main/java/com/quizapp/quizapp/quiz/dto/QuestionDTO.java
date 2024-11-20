package com.quizapp.quizapp.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private String questionText;
    private String topic;
    private List<String> options;
    private String questionType;
    private int correctOptionIndex;
    private String explanation;
}
