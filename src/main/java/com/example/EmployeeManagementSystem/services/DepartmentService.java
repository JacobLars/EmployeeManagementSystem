package com.example.EmployeeManagementSystem.services;

import com.example.EmployeeManagementSystem.entities.Department;
import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.repositories.DepartmentRepository;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public void addEmployeeToDepartment(Employee employee, int departmentId){
        
        System.out.println(employee.getEmail());
        Department department = departmentRepository.findById(departmentId).get();
            
        employee.setDepartment(department);
        department.addEmployeeToDepartment(employee);
        
        departmentRepository.save(department);

    }
   
    public void addDepartment(Department department){
        departmentRepository.save(department);
    }
    
    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }
    
    public Department getDepartmentById(int id){
        return departmentRepository.findById(id).get();
    }
    
    public List<Employee> getDepartmentEmployees(int id){
        return getDepartmentById(id).getEmployees();
    }
    
}
