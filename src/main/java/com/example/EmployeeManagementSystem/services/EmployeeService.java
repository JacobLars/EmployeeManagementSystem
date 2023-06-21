package com.example.EmployeeManagementSystem.services;

import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.repositories.EmployeeRepository;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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

    public List<Employee> getAllEmployees() {
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

        employeeRepository.deleteById(employeeId);
    }
    
    
    
}
