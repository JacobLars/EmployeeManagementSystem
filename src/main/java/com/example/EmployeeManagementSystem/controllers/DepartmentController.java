package com.example.EmployeeManagementSystem.controllers;

import com.example.EmployeeManagementSystem.entities.Department;
import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.services.DepartmentService;
import com.example.EmployeeManagementSystem.services.EmployeeService;
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

    @GetMapping("/company/admin/department/all")
    public String getHomePage(Model model) {

        List<Department> departments = departmentService.getAllDepartments();

        model.addAttribute("departments", departments);
        return "home";
    }

    public DepartmentController(DepartmentService companyService,
            EmployeeService employeeService) {
        this.departmentService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping("/company/admin/department/add")
    public String getAddDepartmentPage(Model model) {

        Department department = new Department();
        model.addAttribute("department", department);
        return "addDepartment";
    }

    @PostMapping("/company/department/save")
    public String addDepartment(Department department) {
        departmentService.addDepartment(department);

        return "redirect:/company/home";
    }

    @GetMapping("/company/admin/department/edit/{departmentId}")
    public String getEditDepartmentPage(@PathVariable("departmentId") int departmentId, Model model) {

        Department department = departmentService.getDepartmentById(departmentId);
        Employee employee = new Employee();

        model.addAttribute("employee", employee);
        model.addAttribute("department", department);
        model.addAttribute("departmentId", departmentId);
        return "editDepartment";
    }

    @PostMapping("/company/department/add/employee/{departmentId}")
    public String addEmployeeToDepartment(Employee employee, @PathVariable("departmentId") int departmentId) {
        System.out.println(departmentId);
        System.out.println(employee.getFirstname());
        employeeService.saveEmployee(employee);
        Employee theEmployee = employeeService.getEmployeeById(employee.getEmail());
        departmentService.addEmployeeToDepartment(theEmployee, departmentId);
        return "redirect:/company/admin/employees/all";
    }

    @GetMapping("/company/admin/department/{departmentId}/employees")
    public String getDepartmentEmployees(Model model, @PathVariable("departmentId") int departmentId) {

        List<Employee> employees = departmentService.getDepartmentEmployees(departmentId);
        model.addAttribute("employees", employees);

        return "employees";
    }

}
