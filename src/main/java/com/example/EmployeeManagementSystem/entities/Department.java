
package com.example.EmployeeManagementSystem.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Employee> employees;

    public Department() {
    }
    
    
    public Department(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    
    public void addEmployeeToDepartment(Employee employee){
        employees.add(employee);
    }
    
    public void deleteEmployeeFromDepartment(Employee employee){
        employees.remove(employee);
    }
    
}
