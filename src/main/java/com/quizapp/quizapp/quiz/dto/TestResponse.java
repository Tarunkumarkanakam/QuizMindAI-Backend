package com.quizapp.quizapp.quiz.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestResponse {
    private String examId;
    private String examName;
    private String examDesc;
    private LocalDateTime examStartTime;
    private LocalDateTime examEndTime;
}
