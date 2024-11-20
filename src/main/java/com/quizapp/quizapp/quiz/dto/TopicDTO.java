package com.quizapp.quizapp.quiz.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private List<QuestionAnswersDTO> questionAnswers;
}
