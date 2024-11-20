package com.quizapp.quizapp.core.auth.controller;

import com.quizapp.quizapp.core.auth.dto.AdminRegistrationRequest;
import com.quizapp.quizapp.core.auth.dto.AdminRegistrationResponse;
import com.quizapp.quizapp.core.auth.service.AuthenticationService;
import com.quizapp.quizapp.quiz.email.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
@Tag(name = "Admin-User Registration")
@Slf4j
public class UserRegistrationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/registerUser")
//    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AdminRegistrationResponse> register(
            @RequestBody @Valid AdminRegistrationRequest request,
            @RequestParam String adminEmail
    ) throws MessagingException, UnsupportedEncodingException {
        log.info("Registering user {}", request.getEmail() + "By admin email " + adminEmail);
        var response = authenticationService.register(request, adminEmail);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/registerUsers")
//    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<AdminRegistrationResponse>> bulkRegister(
            @RequestBody @Valid List<AdminRegistrationRequest> requests,
            @RequestParam String adminEmail
    ) throws MessagingException, UnsupportedEncodingException {
        log.info("Registering {} users", requests.size());
        List<AdminRegistrationResponse> responses = new ArrayList<>();

        for (AdminRegistrationRequest request : requests) {
            log.info("Registering user {}", request.getEmail());
            var response = authenticationService.register(request, adminEmail);
            responses.add(response);
        }

        return ResponseEntity.ok(responses);
    }

}
