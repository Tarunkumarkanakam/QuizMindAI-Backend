package com.quizapp.quizapp.core.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdminRegistrationResponse {
    private String userEmail;
    private String password;
}
