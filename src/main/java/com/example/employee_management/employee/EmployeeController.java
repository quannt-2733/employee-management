package com.example.employee_management.employee;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // URL: GET http://localhost:8088/api/v1/employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }

    // URL: POST http://localhost:8088/api/v1/employees
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee newEmployee) {
        Employee savedEmployee = employeeService.createEmployee(newEmployee);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployee);
    }

    // URL: GET http://localhost:8088/api/v1/employees/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);

        return ResponseEntity.ok(employee);
    }

    // URL: PUT http://localhost:8088/api/v1/employees/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails);

        return ResponseEntity.ok(updatedEmployee);
    }
    // URL: DELETE http://localhost:8088/api/v1/employees/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
    // URL: GET /api/v1/employees/search?name=John
    // URL: GET /api/v1/employees/search?departmentId=1
    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long departmentId) {

        List<Employee> result = employeeService.searchEmployees(name, departmentId);
        return ResponseEntity.ok(result);
    }

    // URL: GET /api/v1/employees/count
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getEmployeeCount() {
        long count = employeeService.countEmployees();

        return ResponseEntity.ok(Map.of("totalEmployees", count));
    }
}
