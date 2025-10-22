package com.example.employee_management.utility;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class UtilityService {
    private final AtomicLong employIdCounter = new AtomicLong();

    // Tạo mã nhân viên mới theo format EMP + số nguyên tự tăng
    public String generateEmployeeId() {
        long id = employIdCounter.incrementAndGet();
        return String.format("EMP%05d", id); // EMP00001, EMP00002, ...
    }

    /**
     * Hàm tiện ích để chuẩn hóa tên
     * Ví dụ: " nGuyeN vAn a " -> "Nguyen Van A"
     * @param name Tên cần chuẩn hóa
     * @return Tên đã chuẩn hóa
     */
    public String standardizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }

        // Loại bỏ khoảng trắng thừa ở đầu và cuối, chuyển thành chữ thường
        String[] parts = name.trim().toLowerCase().split("\\s+");
        StringBuilder standardized = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                // Viết hoa chữ cái đầu tiên của mỗi phần tên
                standardized.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1))
                        .append(" ");
            }
        }
        return standardized.toString().trim();
    }
}
