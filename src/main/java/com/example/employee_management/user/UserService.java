package com.example.employee_management.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Hàm ví dụ để đăng ký người dùng mới.
     * @param username Tên đăng nhập
     * @param rawPassword Mật khẩu thô (chưa mã hóa)
     */
    public void registerUser(String username, String rawPassword) {
        // 1. Mã hóa mật khẩu thô
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 2. Tạo đối tượng User mới
        // User newUser = new User();
        // newUser.setUsername(username);
        // newUser.setPassword(encodedPassword);

        // 3. Lưu vào database (sẽ làm ở Module 3)
        // userRepository.save(newUser);

        System.out.println("Đăng ký người dùng: " + username);
        System.out.println("Mật khẩu thô: " + rawPassword);
        System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);
    }
}
