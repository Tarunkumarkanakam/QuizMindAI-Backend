package com.quizapp.quizapp.quiz.controller;

import com.quizapp.quizapp.quiz.service.MCQService;
import com.quizapp.quizapp.quiz.dto.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("questions")
@RequiredArgsConstructor
@Tag(name = "Questions")
public class MCQController {
    private final MCQService mcqService;

    @PostMapping("/save-questions")
    public ResponseEntity<?> saveQuestions(
            @RequestBody SaveQuestionsRequest request) {
        try {
            mcqService.saveQuestions(request.getExamId(), request.getQuestions());
            return ResponseEntity.ok("Questions saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to save questions.");
        }
    }

}
