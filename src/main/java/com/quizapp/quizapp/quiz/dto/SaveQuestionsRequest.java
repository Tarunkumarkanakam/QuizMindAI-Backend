package com.quizapp.quizapp.quiz.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveQuestionsRequest {
    private String examId;
    private List<QuestionDTO> questions;
}
