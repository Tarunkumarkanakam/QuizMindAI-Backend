package com.quizapp.quizapp.core.auth.service;

import com.quizapp.quizapp.core.auth.dto.*;
import com.quizapp.quizapp.core.auth.util.ResourceNotFoundException;
import com.quizapp.quizapp.core.config.JwtService;
import com.quizapp.quizapp.core.model.Role;
import com.quizapp.quizapp.core.model.User;
import com.quizapp.quizapp.core.repository.RoleRepository;
import com.quizapp.quizapp.core.repository.UserRepository;
import com.quizapp.quizapp.quiz.email.service.EmailService;
import com.quizapp.quizapp.quiz.email.utils.EmailTemplateName;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import static com.quizapp.quizapp.core.auth.util.ReadablePasswordGenerator.generateStrongPassword;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final EmailService emailService;

    public void register(RegistrationRequest request) {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalMonitorStateException("User role not exist"));
        var user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
    }
    
    public AdminRegistrationResponse register(AdminRegistrationRequest request, String adminEmail) throws MessagingException, UnsupportedEncodingException {
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalMonitorStateException("User role not exist"));
        String password = generateStrongPassword();
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(password))
                .accountLocked(false)
                .enabled(true)
                .roles(List.of(userRole))
                .logUserId(userRepository.findByEmail(adminEmail).orElseThrow(() -> new ResourceNotFoundException("Admin not found")).getId())
                .build();
        userRepository.save(user);
//        emailService.sendEmail(
//                request.getEmail(),
//                request.getEmail(),
//                EmailTemplateName.USER_REGISTER,
//                "https://questai.navigatelabsai.com",
//                password,
//                "Welcome to QuestAI");
//        emailService.sendEmail(
//                EmailTemplateName.EXAM_USER_REGISTER,
//                request.getEmail(),
//                "28th August 2024",
//                "2 pm",
//                "90 minutes",
//                password,
//                "Welcome to QuestAI Navigate Labs");
        return AdminRegistrationResponse.builder()
                .userEmail(request.getEmail())
                .password(password)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var claims = new HashMap<String, Object>();
            var user = (User) auth.getPrincipal();
            var jwtToken = jwtService.generateToken(claims, user);
//        saveUserToken(user, jwtToken);
            return AuthenticationResponse.builder()
                    .role(user.getRoles().get(0).getName())
                    .token(jwtToken)
                    .enabled(user.getEnabled())
                    .build();
        }
        catch (Exception e) {
            throw new UsernameNotFoundException(" Invalid credentials. ");
        }


    }



//    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType("Bearer")
//                .createdAt(LocalDateTime.now())
//                .expiresAt(LocalDateTime.now())
//                .build();
//        tokenRepository.save(token);
//    }
}
