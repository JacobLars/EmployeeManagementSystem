
package com.example.EmployeeManagementSystem.security;

import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.repositories.EmployeeRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Optional<Employee> optionalEmployee = employeeRepository.findById(email);
        
        Employee employee = optionalEmployee.orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(),
                employee.getPassword(),
                mapRolesToAuthorities(employee.isIsAdmin())
        );
    }
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(boolean isAdmin) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
        }
        return authorities;
    }
}
