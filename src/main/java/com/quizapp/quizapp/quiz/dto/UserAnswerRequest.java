package com.quizapp.quizapp.quiz.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerRequest {
    private String examId;
    private String email;
}
