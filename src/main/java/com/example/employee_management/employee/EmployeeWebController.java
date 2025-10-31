package com.example.employee_management.employee;

import com.example.employee_management.department.DepartmentService;
import com.example.employee_management.dto.DepartmentStatisticDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeWebController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeWebController(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    /**
     * URL: GET /employees
     */
    @GetMapping
    public String showEmployeeList(Model model,
                                   @RequestParam(required = false) String name,
                                   @RequestParam(required = false) Long departmentId) {

        List<Employee> employees = employeeService.searchEmployees(name, departmentId);

        model.addAttribute("employees", employees);
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("searchName", name);
        model.addAttribute("searchDeptId", departmentId);

        return "employee-list";
    }

    /**
     * URL: GET /employees/add
     */
    @GetMapping("/add")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.getAllDepartments());

        return "employee-form";
    }

    /**
     * URL: POST /employees/save
     */
    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
                               BindingResult bindingResult, 
                               Model model,
                               RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("errorMessage", "Please correct the errors in the form.");

            return "employee-form";
        }

        try {
            if (employee.getId() == null) {
                employeeService.createEmployee(employee);
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Add employee '" + employee.getName() + "' successfully!");
            } else {
                employeeService.updateEmployee(employee.getId(), employee);
                redirectAttributes.addFlashAttribute("successMessage", 
                    "Update employee '" + employee.getName() + "' successfully!");
            }

            return "redirect:/employees";
        } catch (Exception e) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("errorMessage", 
                "Error: " + e.getMessage());
            e.printStackTrace();

            return "employee-form";
        }
    }

    /**
     * URL: GET /employees/statistics
     */
    @GetMapping("/statistics")
    public String showStatisticsPage(Model model) {
        List<DepartmentStatisticDTO> departmentStats = employeeService.getDepartmentStatistics();
        long totalEmployees = employeeService.countEmployees();

        model.addAttribute("departmentStats", departmentStats);
        model.addAttribute("totalEmployees", totalEmployees);

        return "employee-statistics";
    }
}
