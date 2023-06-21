package com.example.EmployeeManagementSystem.security;


import static com.example.EmployeeManagementSystem.security.UserRole.ADMIN;
import static com.example.EmployeeManagementSystem.security.UserRole.EMPLOYEE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final PasswordEncoder PasswordEncoder;
    
    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    
    private final CustomUserDetailsService customUserDetailsService;
    
    @Autowired
    public SecurityConfig(PasswordEncoder PasswordEncoder,
            CustomAuthenticationSuccessHandler authenticationSuccessHandler,
            CustomUserDetailsService customerUserDetialsService) {
        this.PasswordEncoder = PasswordEncoder;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.customUserDetailsService = customerUserDetialsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http.authorizeRequests()
                .antMatchers("/company/admin/**").hasRole(ADMIN.name())
                .antMatchers("/company/employee/**").hasRole(EMPLOYEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler);
                //.defaultSuccessUrl("/company/admin/department/all", true);
        
       
    
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }

}

