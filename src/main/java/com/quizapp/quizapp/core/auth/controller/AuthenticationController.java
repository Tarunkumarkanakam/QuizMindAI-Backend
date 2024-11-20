package com.quizapp.quizapp.core.auth.controller;

import com.quizapp.quizapp.core.auth.dto.AuthenticationRequest;
import com.quizapp.quizapp.core.auth.dto.AuthenticationResponse;
import com.quizapp.quizapp.core.auth.dto.RegistrationRequest;
import com.quizapp.quizapp.core.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }


    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest authenticationRequest
    ) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(authenticationRequest);
        return ResponseEntity.ok(authenticationResponse);
    }

}
