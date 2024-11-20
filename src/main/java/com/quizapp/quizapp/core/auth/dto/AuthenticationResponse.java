package com.quizapp.quizapp.core.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthenticationResponse {
    private String token;
    private String role;
    private Boolean enabled;
}
