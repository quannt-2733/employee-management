package com.example.employee_management.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Spring Data JPA auto generate query: SELECT * FROM employees WHERE name LIKE '%<name>%'
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Query: SELECT * FROM employees WHERE department_id = <departmentId>
    List<Employee> findByDepartmentId(Long departmentId);
}
