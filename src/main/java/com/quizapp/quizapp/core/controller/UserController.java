package com.quizapp.quizapp.core.controller;

import com.quizapp.quizapp.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(@RequestParam String role, @RequestParam String adminEmail) {
        var data = userService.findByRoleByAdmin(role, adminEmail);
        List<Map<String, Object>> users = new ArrayList<>();
        for (var user : data) {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("userEmail",user.getUsername());
            userMap.put("createdAt",user.getCreatedDate());
            users.add(userMap);
        }
        return ResponseEntity.ok(users);
    }
}
