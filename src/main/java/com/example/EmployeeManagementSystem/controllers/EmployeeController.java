package com.example.EmployeeManagementSystem.controllers;

import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.entities.TimeReport;
import com.example.EmployeeManagementSystem.services.EmployeeService;
import com.example.EmployeeManagementSystem.services.TimeReportService;
import java.security.Principal;
import java.time.LocalDate;
import java.time.Month;
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

    @GetMapping("/company/admin/home")
    public String getAdminHomePage(Model model, Principal principal) {
        String username = principal.getName();
        Employee employee = employeeService.getEmployeeById(username);

        List<TimeReport> reportsFromCurrentMonth
                = timeReportService.getEmployeesTimeReportByCurrentMonth(employee);

        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("imageUrl", employee.getImageUrl());
        model.addAttribute("employeeId", employee.getEmail());
        model.addAttribute("timeReports", reportsFromCurrentMonth);
        model.addAttribute("month", "Your reports from " + LocalDate.now().getMonth());
        model.addAttribute("allReportsText", "View all reports");
        return "adminhome";
    }

    @GetMapping("/company/employees/task/all")
    public String getAllReports(Model model, Principal principal) {

        String username = principal.getName();
        Employee employee = employeeService.getEmployeeById(username);

        List<TimeReport> allReports
                = employeeService.sortEmployeeReports(employee.getTimeReports());

        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("imageUrl", employee.getImageUrl());
        model.addAttribute("employeeId", employee.getEmail());
        model.addAttribute("timeReports", allReports);
        model.addAttribute("month", "All Reports");
        model.addAttribute("allReportsText", "");
        if (employee.isIsAdmin()) {
            return "adminhome";
        }
        return "employeehome";
    }

    @GetMapping("/company/employee/home")
    public String getHomepage(Principal principal, Model model) {

        String username = principal.getName();
        Employee employee = employeeService.getEmployeeById(username);
        Month month = LocalDate.now().getMonth();
        
        List<TimeReport> reportsFromCurrentMonth
                = timeReportService.getEmployeesTimeReportByCurrentMonth(employee);

        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("imageUrl", employee.getImageUrl());
        model.addAttribute("employeeId", employee.getEmail());
        model.addAttribute("timeReports", reportsFromCurrentMonth);
        model.addAttribute("month", month);
        model.addAttribute("allReportsText", "View all reports");
        return "employeehome";
    }

    @GetMapping("/company/admin/employees/all")
    public String getEmployeesPage(Model model, Principal principal) {

        List<Employee> employees = employeeService.getEmployees();
        String username = principal.getName();
        Employee employee = employeeService.getEmployeeById(username);

        model.addAttribute("employees", employees);
        model.addAttribute("firstname", employee.getFirstname());
        model.addAttribute("imageUrl", employee.getImageUrl());
        model.addAttribute("employeeId", employee.getEmail());
        return "employees";
    }

    @GetMapping("/company/employees/delete/{employeeId}")
    public String deleteEmployeeById(@PathVariable("employeeId") String employeeId) {

        employeeService.deleteEmployee(employeeId);
        return "redirect:/company/admin/employees/all";
    }

    @GetMapping("/company/employees/edit/{employeeId}")
    public String editEmployeePage(Model model,
            @PathVariable("employeeId") String employeeId,
            Principal principal) {

        Employee employee = employeeService.getEmployeeById(employeeId);
        String username = principal.getName();
        System.out.println(username);
        Employee loggedInEmployee = employeeService.getEmployeeById(username);
        model.addAttribute("employee", employee);
        model.addAttribute("firstname", loggedInEmployee.getFirstname());
        model.addAttribute("imageUrl", loggedInEmployee.getImageUrl());
        return "editEmployee";
    }

    @PostMapping("/company/employees/save")
    public String saveEmployee(Employee employee) {

        employeeService.saveEmployee(employee);

        return "redirect:/company/admin/employees/all";
    }

    @GetMapping("/company/employees/task/add/{employeeId}")
    public String getAddTaskPage(Model model,
            @PathVariable("employeeId") String employeeId,
            Principal principal) {

        Employee employee = employeeService.getEmployeeById(employeeId);
        TimeReport timeReport = new TimeReport();

        model.addAttribute("employee", employee);
        model.addAttribute("timeReport", timeReport);

        return "addTask";
    }

    @PostMapping("/company/employees/task/save/{employeeId}")
    public String saveTimeReportToEmployee(TimeReport timeReport, @PathVariable("employeeId") String employeeId) {

        Employee employee = employeeService.getEmployeeById(employeeId);
        timeReportService.saveTimeReport(timeReport);
        employeeService.addTimeReportToEmployee(employeeId, timeReport);

        if (employee.isIsAdmin()) {
            return "redirect:/company/admin/home";

        }
        return "redirect:/company/employee/home";
    }

    @GetMapping("/company/admin/employees/reports/all/{employeeId}")
    public String getEmployeesTimeReports(@PathVariable("employeeId") String employeeId, Model model, Principal principal) {

        Employee employee = employeeService.getEmployeeById(employeeId);
        Employee loggedInEmployee = employeeService.getEmployeeById(principal.getName());

        List<TimeReport> allReports
                = employeeService.sortEmployeeReports(employee.getTimeReports());

        model.addAttribute("firstname", loggedInEmployee.getFirstname());
        model.addAttribute("imageUrl", loggedInEmployee.getImageUrl());
        model.addAttribute("employeeId", loggedInEmployee.getEmail());
        model.addAttribute("timeReports", allReports);
        model.addAttribute("month", "Reports from " + employee.getFirstname());
        return "employeeTimeReports";
    }

}
