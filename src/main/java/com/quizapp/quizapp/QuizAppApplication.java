package com.quizapp.quizapp;

import com.quizapp.quizapp.core.model.Role;
import com.quizapp.quizapp.core.model.User;
import com.quizapp.quizapp.core.repository.RoleRepository;
import com.quizapp.quizapp.core.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class QuizAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(Role.builder().name("USER").build());
            }
            if (roleRepository.findByName("ADMIN").isEmpty()) {
                roleRepository.save(Role.builder().name("ADMIN").build());
            }
            if (userRepository.findByRoles_Name("ADMIN").isEmpty()) {
                var adminRole = roleRepository.findByName("ADMIN")
                        .orElseThrow(() -> new IllegalMonitorStateException("ADMIN role not exist"));
                userRepository.save(User.builder()
                        .firstname("Admin")
                        .lastname("Admin")
                        .email("admin@quizapp.com")
                        .password(passwordEncoder.encode("adminuser"))
                        .createdDate(LocalDateTime.now())
                        .roles(List.of(adminRole))
                        .enabled(true)
                        .accountLocked(false)
                        .build()
                );
            }
        };
    }

}
