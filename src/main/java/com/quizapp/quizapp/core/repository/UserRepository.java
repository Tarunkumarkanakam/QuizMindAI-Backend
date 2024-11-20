package com.quizapp.quizapp.core.repository;

import com.quizapp.quizapp.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    List<User> findByRoles_Name(String roleName);

    @Query(" select u from User u where u.logUserId = :adminEmail")
    List<User> findUsersByLogUserAndRole(Integer adminEmail);

//    @Query(value = """
//    SELECT u FROM User u \s
//    where u.email in :email \s
//    """
//    )
//    List<User> findByEmails(List<String> emails);
}
