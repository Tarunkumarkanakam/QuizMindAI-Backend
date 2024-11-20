package com.quizapp.quizapp.quiz.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionDataDTO {
    private Long id;
    private String sessionId;
    private String userEmail;
    private LocalDateTime examStartTime;
    private LocalDateTime examEndTime;
    private String examId;
    private Boolean lockStatus;
}
