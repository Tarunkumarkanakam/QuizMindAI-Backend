package com.quizapp.quizapp.quiz.controller;

import com.quizapp.quizapp.core.auth.dto.RegistrationRequest;
import com.quizapp.quizapp.quiz.dto.CreateExamRequest;
import com.quizapp.quizapp.quiz.dto.ExamDataResponse;
import com.quizapp.quizapp.quiz.dto.TestResponse;
import com.quizapp.quizapp.quiz.model.Session;
import com.quizapp.quizapp.quiz.service.ExamService;
import com.quizapp.quizapp.quiz.service.SessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("exam")
@RequiredArgsConstructor
@Tag(name = "Examination")
public class ExamController {

    private final ExamService examService;
    private final SessionService sessionService;

    @PostMapping("/createExam")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> createExam(
            @RequestBody @Valid CreateExamRequest request
    ) {
        examService.createExam(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/getExamData")
    public ResponseEntity<?> getExamData(
            @RequestParam String email, @RequestParam String examId
    ) {

        Session session = sessionService.getSessionfromEmailAndExamId(email, examId);

        if (session == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Session not found for email: " + email);
        }

        if (!session.getLockStatus()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access not permitted.");
        }

        sessionService.lockSession(session);

        LocalDateTime startTime = session.getExamStart();
        LocalDateTime endTime = session.getExamEnd();
        LocalDateTime currentTime = LocalDateTime.now();

        if (ChronoUnit.MINUTES.between(currentTime, startTime) > 30 || currentTime.isAfter(endTime)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to access the exam data at this time.");
        }
        var testResponseList = examService.findQuestionsByExamId(examId);
        return ResponseEntity.ok(testResponseList);
    }

}
