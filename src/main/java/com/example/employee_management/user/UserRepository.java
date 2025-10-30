package com.example.employee_management.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Query WHERE LOWER(username) = LOWER(?)
    Optional<User> findByUsernameIgnoreCase(String username);
}
