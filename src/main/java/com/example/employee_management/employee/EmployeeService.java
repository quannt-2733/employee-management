package com.example.employee_management.employee;
import com.example.employee_management.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        existingEmployee.setName(employeeDetails.getName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setDepartment(employeeDetails.getDepartment());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) {
         if (!employeeRepository.existsById(id)) {
             throw new ResourceNotFoundException("Employee not found with id: " + id);
         }
         employeeRepository.deleteById(id);
    }

    public List<Employee> searchEmployees(String name, Long departmentId) {
        if (name != null && !name.trim().isEmpty()) {
            return employeeRepository.findByNameContainingIgnoreCase(name);
        }
        if (departmentId != null) {
            return employeeRepository.findByDepartmentId(departmentId);
        }

        return employeeRepository.findAll();
    }
}
