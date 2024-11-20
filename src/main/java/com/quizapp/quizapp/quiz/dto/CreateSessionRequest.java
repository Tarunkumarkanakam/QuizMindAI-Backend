package com.quizapp.quizapp.quiz.dto;

import com.quizapp.quizapp.core.model.User;
import com.quizapp.quizapp.quiz.model.Exam;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSessionRequest {
    private String sessionId;
    private LocalDateTime examStart;
    private LocalDateTime examEnd;
    private String examId;
    private List<String> emails;
    private String adminEmail;
}
