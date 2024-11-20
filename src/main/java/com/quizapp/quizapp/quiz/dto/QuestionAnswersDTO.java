package com.quizapp.quizapp.quiz.dto;

import com.quizapp.quizapp.quiz.model.Answer;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAnswersDTO {
    private int id;
    private String question;
    private LinkedHashMap<Integer, String> answer;
}
