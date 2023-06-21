
package com.example.EmployeeManagementSystem.entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Employee {
    
    @Id
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String imageUrl;
    private int age;
    private boolean isAdmin;
    
    @ManyToOne
    private Department department;

    @OneToMany(cascade = CascadeType.PERSIST)
    List<TimeReport> timeReports;
    
    public Employee() {
    }
 
    public Employee(String email,
            String password,
            String firstname,
            String lastname, 
            String imageUrl, 
            int age, 
            boolean isAdmin) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.imageUrl = imageUrl;
        this.age = age;
        this.isAdmin = isAdmin;
    }
    
    
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<TimeReport> getTimeReports() {
        return timeReports;
    }

    public void setTimeReports(List<TimeReport> timeReports) {
        this.timeReports = timeReports;
    }
    
    
    
}
