package com.quizapp.quizapp.quiz.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerResponse {
    private Integer questionId;
    private String selectedOption;
}
