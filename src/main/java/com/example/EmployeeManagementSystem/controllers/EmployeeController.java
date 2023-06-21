
package com.example.EmployeeManagementSystem.controllers;

import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.entities.TimeReport;
import com.example.EmployeeManagementSystem.services.EmployeeService;
import com.example.EmployeeManagementSystem.services.TimeReportService;
import java.security.Principal;
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
    private final TimeReportService timeReportService;
    
    
    public EmployeeController(EmployeeService employeeService,
                    TimeReportService timeReportService) {
        this.employeeService = employeeService;
        this.timeReportService = timeReportService;
    }
    
    
    @GetMapping("/company/employee/home")
    public String getHomepage(Principal principal, Model model){
        String username = principal.getName();
        Employee employee = employeeService.getEmployeeById(username);
        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("imageUrl", employee.getImageUrl());
        model.addAttribute("employeeId", employee.getEmail());
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
    
    @GetMapping("/company/employees/task/add/{employeeId}")
    public String getAddTaskPage(Model model, @PathVariable("employeeId") String employeeId){
        
        Employee employee = employeeService.getEmployeeById(employeeId);
        TimeReport timeReport = new TimeReport();
        
        model.addAttribute("employee", employee);
        model.addAttribute("timeReport", timeReport);
        
        return "addTask";
    }
    
    @PostMapping("/company/employees/task/save")
    public String saveTimeReport(TimeReport timeReport){
        
        timeReportService.saveTimeReport(timeReport);
        
        return "redirect:/company/employee/home";
    }
    
}
