package com.example.employee_management.user;

import com.example.employee_management.exception.UserAlreadyExistsException;
import com.example.employee_management.role.Role;
import com.example.employee_management.role.RoleRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public User registerUser(String username, String rawPassword) {
        String normalizedUsername = username.toLowerCase();

        if (userRepository.findByUsernameIgnoreCase(normalizedUsername).isPresent()) {
            throw new UserAlreadyExistsException("Username '" + username + "' is already taken.");
        }

        try {
            Role userRole = roleRepository.findByName("USER")
                .orElseGet(() -> roleRepository.save(new Role("USER")));

            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(rawPassword));
            newUser.setRoles(Set.of(userRole));

            return userRepository.save(newUser);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to register user due to database error.", e);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public User registerAdmin(String username, String rawPassword) {
        String normalizedUsername = username.toLowerCase();

        if (userRepository.findByUsernameIgnoreCase(normalizedUsername).isPresent()) {
            throw new UserAlreadyExistsException("Username '" + username + "' is already taken.");
        }

        try {
            Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

            User newAdmin = new User();
            newAdmin.setUsername(username);
            newAdmin.setPassword(passwordEncoder.encode(rawPassword));
            newAdmin.setRoles(Set.of(adminRole));

            return userRepository.save(newAdmin);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to register user due to database error.", e);
        }
    }
}
