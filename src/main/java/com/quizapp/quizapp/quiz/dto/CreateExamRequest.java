package com.quizapp.quizapp.quiz.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateExamRequest {
    private String examId;
    private String examName;
    private String examDescription;
}
