
package com.example.EmployeeManagementSystem.controllers;


import com.example.EmployeeManagementSystem.services.DepartmentService;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CompanyController {


    @GetMapping("/login")
    public String getLoginPage(){

        return "login";
    }
    
}
