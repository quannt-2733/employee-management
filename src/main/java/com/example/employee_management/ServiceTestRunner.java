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
        // 3. Đây là nơi code của bạn sẽ chạy sau khi app khởi động
        System.out.println("=========================================");
        System.out.println("BẮT ĐẦU CHẠY THỬ UserService...");

        // Gọi phương thức bạn muốn kiểm tra
        userService.registerUser("admin", "admin_password123");

        System.out.println("KẾT THÚC CHẠY THỬ.");
        System.out.println("=========================================");
    }
}
