
package com.example.EmployeeManagementSystem.controllers;

import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.services.EmployeeService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("employee")
public class EmployeeController {
    
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    
    @GetMapping("/company/employee/home")
    public String getHomepage(){
        return "employeehome";
    }

    @GetMapping("/company/admin/employees/all")
    public String getEmployeesPage(Model model){
        
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        
        return "employees";
    }
    
    @GetMapping("/company/employees/delete/{employeeId}")
    public String deleteEmployeeById(@PathVariable("employeeId") String employeeId){
        employeeService.deleteEmployee(employeeId);
        return "redirect:/company/admin/employees/all";
    }
    
  
    @GetMapping("/company/employees/edit/{employeeId}")
    public String editEmployeePage(Model model, @PathVariable("employeeId") String employeeId){
        
        Employee employee = employeeService.getEmployeeById(employeeId);
        
        model.addAttribute("employee", employee);
        
        return "editEmployee";
    }
    
    @PostMapping("/company/employees/save")
    public String saveEmployee(Employee employee){
        
        employeeService.saveEmployee(employee);
        
        return "redirect:/company/employees/view";
    }
    
}
