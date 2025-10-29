package com.example.employee_management;

import com.example.employee_management.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServiceTestRunner implements CommandLineRunner {
    private final UserService userService;

    public ServiceTestRunner(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            userService.registerUser("user", "user123");
            System.out.println("CREATED DEFAULT USER: user/user123");

             userService.registerAdmin("admin", "admin123");
            System.out.println("CREATED DEFAULT ADMIN: admin/admin123");
        } catch (Exception e) {
            System.out.println("Default user 'user' might already exist.");
        }
    }
}
