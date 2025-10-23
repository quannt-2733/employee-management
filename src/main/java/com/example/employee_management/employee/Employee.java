package com.example.employee_management.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Automatically generate getter, setter, toString, equals, and hashCode methods.
@NoArgsConstructor // Generate a no-argument constructor.
@AllArgsConstructor // Generate a constructor with all arguments.
public class Employee {
    private Long id;
    private String name;
    private String email;
}
