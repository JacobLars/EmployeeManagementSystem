
package com.example.EmployeeManagementSystem.repositories;

import com.example.EmployeeManagementSystem.entities.TimeReport;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TimeReportRepository extends JpaRepository<TimeReport, Integer>{
    
}
