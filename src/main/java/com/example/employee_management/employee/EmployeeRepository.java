package com.example.employee_management.employee;

import com.example.employee_management.dto.DepartmentStatisticDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Spring Data JPA auto generate query: SELECT * FROM employees WHERE name LIKE '%<name>%'
    List<Employee> findByNameContainingIgnoreCase(String name);

    // Query: SELECT * FROM employees WHERE department_id = <departmentId>
    List<Employee> findByDepartmentId(Long departmentId);

    @Query("SELECT new com.example.employee_management.dto.DepartmentStatisticDTO(e.department.name, COUNT(e.id)) " +
            "FROM Employee e " +
            "WHERE e.department IS NOT NULL " +
            "GROUP BY e.department.name " +
            "ORDER BY COUNT(e.id) DESC")
    List<DepartmentStatisticDTO> countEmployeesByDepartment();
}
