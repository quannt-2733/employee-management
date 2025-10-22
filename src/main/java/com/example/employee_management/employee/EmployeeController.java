package com.example.employee_management.employee;

import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    // A temporary list used to store data (in-memory)
    private final List<Employee> employees = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();

    @PostConstruct
    public void initData() {
        employees.add(new Employee(counter.incrementAndGet(), "John Doe", "john@example.com"));
        employees.add(new Employee(counter.incrementAndGet(), "Jane Smith", "jane@example.com"));
    }

    // URL: GET http://localhost:8088/api/v1/employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        // Return the list of employees and HTTP status 200 (OK)
        return ResponseEntity.ok(employees);
    }

    // URL: POST http://localhost:8088/api/v1/employees
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee newEmployee) {
        newEmployee.setId(counter.incrementAndGet());
        employees.add(newEmployee);

        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }
}
