package com.quizapp.quizapp.quiz.controller;

import com.quizapp.quizapp.quiz.dto.UserAnswerRequest;
import com.quizapp.quizapp.quiz.dto.UserAnswerResponse;
import com.quizapp.quizapp.quiz.model.UserAnswers;
import com.quizapp.quizapp.quiz.service.UserAnswersService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("answers")
@RequiredArgsConstructor
@Tag(name = "UserAnswers")
public class UserAnswersController {
    private final UserAnswersService userAnswersService;

    @PutMapping("/saveUserAnswer")
    public ResponseEntity<?> saveUserAnswer(
            @RequestParam String email,
            @RequestParam String examId,
            @RequestParam Integer questionId,
            @RequestParam Integer optionId
    ) {
        userAnswersService.saveOrUpdateUserAnswer(email, examId, questionId, optionId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/getAllUserAnswers")
    public ResponseEntity<?> getAllUserAnswers(@RequestBody UserAnswerRequest request) {
        List<UserAnswerResponse> answers = userAnswersService.getUserAnswers(request.getExamId(), request.getEmail());
        return ResponseEntity.ok().body(answers);
    }
}
