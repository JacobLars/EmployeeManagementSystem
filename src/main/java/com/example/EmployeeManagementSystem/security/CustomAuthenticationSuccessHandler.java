package com.example.EmployeeManagementSystem.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response, 
            Authentication authentication) throws IOException, ServletException {
        
          if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            setDefaultTargetUrl("/company/admin/home");
        } else if (authentication.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_EMPLOYEE"))) {
            setDefaultTargetUrl("/company/employee/home");
        }
        super.onAuthenticationSuccess(request, response, authentication); 
    }
    
    
    
}
