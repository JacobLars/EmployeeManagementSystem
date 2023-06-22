package com.example.EmployeeManagementSystem.services;

import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.entities.TimeReport;
import com.example.EmployeeManagementSystem.repositories.EmployeeRepository;
import com.example.EmployeeManagementSystem.repositories.TimeReportRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TimeReportRepository timeReportRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public EmployeeService(EmployeeRepository employeeRepository,
            TimeReportRepository timeReportRepository) {
        this.employeeRepository = employeeRepository;
        this.timeReportRepository = timeReportRepository;
    }

    public void saveEmployee(Employee employee) {

        String encodedPass = bCryptPasswordEncoder.encode(employee.getPassword());

        employee.setPassword(encodedPass);

        if (employee.getImageUrl().isEmpty()) {
            employee.setImageUrl("https://img.freepik.com/free-icon/user_318-159711.jpg");
        }

        employeeRepository.save(employee);
    }

    public Employee getEmployeeById(String employeeEmail) {
        return employeeRepository.findById(employeeEmail).get();
    }

    public List<Employee> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        List<Employee> nonAdminEmployees = employees.stream()
                .filter(employee -> employee.isIsAdmin() == false)
                .collect(Collectors.toList());

        return nonAdminEmployees;
    }

    public void deleteEmployee(String employeeId) {
    Employee employee = getEmployeeById(employeeId);

    if (employee.getDepartment() != null) {
        employee.getDepartment().deleteEmployeeFromDepartment(employee);
        employee.setDepartment(null);
    }

    List<TimeReport> timeReports = new ArrayList<>(employee.getTimeReports()); // Create a copy of the timeReports collection
    
    for (TimeReport timeReport : timeReports) {
        employee.deleteTimeReport(timeReport);
        timeReportRepository.delete(timeReport);
    }

    employeeRepository.deleteById(employeeId);
    }

    public void addTimeReportToEmployee(String employeeId, TimeReport timeReport) {

        Employee employee = getEmployeeById(employeeId);

        timeReport.setEmployee(employee);
        employee.addTimeReport(timeReport);

        employeeRepository.save(employee);
    }

    public List<TimeReport> sortEmployeeReports(List<TimeReport> timeReports) {

        List<TimeReport> sortedTimeReports = timeReports.stream()
                .sorted(Comparator.comparing(TimeReport::getDate))
                .collect(Collectors.toList());

        return sortedTimeReports;
    }

}
