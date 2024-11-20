package com.quizapp.quizapp.core.service;

import com.quizapp.quizapp.core.auth.util.ResourceNotFoundException;
import com.quizapp.quizapp.core.model.User;
import com.quizapp.quizapp.core.repository.RoleRepository;
import com.quizapp.quizapp.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

//    public List<User> findByRole(String name) {
//        return userRepository.findByRoles_Name(name);
//    }

//    findUsersByLogUserAndRole

    public List<User> findByRoleByAdmin(String roleName, String adminEmail) {
        var userId = userRepository.findByEmail(adminEmail).orElseThrow(() -> new ResourceNotFoundException("Admin not found")).getId();
        var data = userRepository.findUsersByLogUserAndRole(userId);
        var role = roleRepository.findByName(roleName).orElseThrow(()->new ResourceNotFoundException("No role found"));
        List<User> users = new ArrayList<>();
        for (var i = 0; i < data.size(); i++) {
            if (data.get(i).getRoles().contains(role)) {
                users.add(data.get(i));
            }
        }
        return users;
    }
}
