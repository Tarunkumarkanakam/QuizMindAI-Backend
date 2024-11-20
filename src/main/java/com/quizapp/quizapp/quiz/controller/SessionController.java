package com.quizapp.quizapp.quiz.controller;

import com.quizapp.quizapp.quiz.dto.CreateExamRequest;
import com.quizapp.quizapp.quiz.dto.CreateSessionRequest;
import com.quizapp.quizapp.quiz.dto.SessionDataDTO;
import com.quizapp.quizapp.quiz.model.Session;
import com.quizapp.quizapp.quiz.service.SessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("session")
@RequiredArgsConstructor
@Tag(name = "Session")
public class SessionController {
    private final SessionService sessionService;

    @PostMapping("/createSession")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid CreateSessionRequest request
    ) {
        sessionService.createSession(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/test")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> getAllTestsofUser(
            @RequestParam String email
    ) {
        var resp = sessionService.findAllTestsofUser(email);
        return ResponseEntity.accepted().body(resp);
    }

    //    @PostMapping("/updateSession")
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public ResponseEntity<?> updateSession(
//            @RequestBody @Valid CreateSessionRequest request
//    ) {
//        sessionService.createSession(request);
//        return ResponseEntity.accepted().build();
//    }
    @PutMapping("/updateSession")
    public ResponseEntity<SessionDataDTO> updateSession(@RequestBody SessionDataDTO sessionDetails, @RequestParam String email) {
        SessionDataDTO updatedSession = sessionService.updateSession(sessionDetails, email);
        return ResponseEntity.ok(updatedSession);
    }

    @DeleteMapping("/deleteSession")
    public ResponseEntity<Void> deleteSession(@RequestParam Long sessionId) {
        sessionService.deleteSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllSessionsOnAdmin")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> getAllSessionsOnAdmin (
            @RequestParam String email
    ) {
        var resp = sessionService.getAllSessionsOnAdmin(email);
        return ResponseEntity.accepted().body(resp);
    }

    @GetMapping("/getAllSessions")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> getAllSessions (
            @RequestParam String email, @RequestParam String sessionId
    ) {
        var resp = sessionService.getAllSessions(email, sessionId);
        return ResponseEntity.accepted().body(resp);
    }


}
