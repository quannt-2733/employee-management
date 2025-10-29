package com.example.employee_management.employee;

import com.example.employee_management.exception.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private static final Logger logger = LogManager.getLogger(EmployeeService.class);
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee) {
        logger.info("Creating new employee: {}", employee.getName());

        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        logger.info("Attempting to update employee with ID: {}", id);

        Employee existingEmployee = employeeRepository.findById(id)
            .orElseThrow(() -> {
                logger.warn("Update failed. Employee not found with ID: {}", id);

                return new ResourceNotFoundException("Employee not found with id: " + id);
            });

        existingEmployee.setName(employeeDetails.getName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setDepartment(employeeDetails.getDepartment());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        logger.info("Successfully updated employee with ID: {}", id);
        return updatedEmployee;
    }

    public void deleteEmployee(Long id) {
        logger.info("Attempting to delete employee with ID: {}", id);

        if (!employeeRepository.existsById(id)) {
            logger.warn("Delete failed. Employee not found with ID: {}", id);
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
        logger.info("Successfully deleted employee with ID: {}", id);
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

    @Cacheable(cacheNames = "employeeCount")
    public long countEmployees() {
        logger.info("Cache missed! Fetching employee count from Database...");

        return employeeRepository.count();
    }
}
