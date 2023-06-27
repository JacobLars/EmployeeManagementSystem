package com.example.EmployeeManagementSystem.controllers;

import com.example.EmployeeManagementSystem.entities.Department;
import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.services.DepartmentService;
import com.example.EmployeeManagementSystem.services.EmployeeService;
import java.security.Principal;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("department")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService companyService,
            EmployeeService employeeService) {
        this.departmentService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping("/company/admin/department/all")
    public String getHomePage(Principal principal, Model model) {

        List<Department> departments = departmentService.getAllDepartments();
        String username = principal.getName();
        Employee employee = employeeService.getEmployeeById(username);
        model.addAttribute("employeeId", employee.getEmail());
        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("imageUrl", employee.getImageUrl());
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping("/company/admin/department/add")
    public String getAddDepartmentPage(Model model, Principal principal) {
        String username = principal.getName();
        Employee employee = employeeService.getEmployeeById(username);

        Department department = new Department();
        model.addAttribute("department", department);
        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("imageUrl", employee.getImageUrl());
        model.addAttribute("employeeId", employee.getEmail());
        return "addDepartment";
    }

    @PostMapping("/company/admin/department/save")
    public String addDepartment(Department department) {
        departmentService.addDepartment(department);

        return "redirect:/company/admin/department/all";
    }

    @GetMapping("/company/admin/department/edit/{departmentId}")
    public String getEditDepartmentPage(
            @PathVariable("departmentId") int departmentId, Model model,
            Principal principal) {

        Employee loggedInEmployee = employeeService.getEmployeeById(principal.getName());
        Department department = departmentService.getDepartmentById(departmentId);
        Employee employee = new Employee();
        System.out.println(principal.getName());
        model.addAttribute("employeeId", principal.getName());
        model.addAttribute("firstname", loggedInEmployee.getFirstname());
        model.addAttribute("imageUrl", loggedInEmployee.getImageUrl());
        model.addAttribute("employee", employee);
        model.addAttribute("department", department);
        model.addAttribute("departmentId", departmentId);
        return "editDepartment";
    }

    @PostMapping("/company/admin/department/add/employee/{departmentId}")
    public String addEmployeeToDepartment(Employee employee, @PathVariable("departmentId") int departmentId) {
        employeeService.saveEmployee(employee);
        Employee theEmployee = employeeService.getEmployeeById(employee.getEmail());
        departmentService.addEmployeeToDepartment(theEmployee, departmentId);
        return "redirect:/company/admin/employees/all";
    }

    @GetMapping("/company/admin/department/{departmentId}/employees")
    public String getDepartmentEmployees(Model model,
            @PathVariable("departmentId") int departmentId,
            Principal principal) {

        Employee employee = employeeService.getEmployeeById(principal.getName());

        List<Employee> employees = departmentService.getDepartmentEmployees(departmentId);
        model.addAttribute("employees", employees);
        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("imageUrl", employee.getImageUrl());
        model.addAttribute("employeeId", employee.getEmail());

        return "employees";
    }

}
