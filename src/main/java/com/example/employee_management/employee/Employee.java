package com.example.employee_management.employee;

import com.example.employee_management.department.Department;
import jakarta.persistence.*;
import lombok.Data;

@Data // Automatically generate getter, setter, toString, equals, and hashCode methods.
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;
}
