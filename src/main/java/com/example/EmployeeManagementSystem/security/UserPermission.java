
package com.example.EmployeeManagementSystem.security;


public enum UserPermission {
    
    EMPLOYEE_READ("employee:read"),
    EMPLOYEE_WRITE("employee:write"),
    DEPARTMENT_WRITE("department:write"),
    DEPARTMENT_READ("department:read");
    
    private final String permission;

    private UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
    
    
    
}
