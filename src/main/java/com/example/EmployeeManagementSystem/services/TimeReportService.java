
package com.example.EmployeeManagementSystem.services;

import com.example.EmployeeManagementSystem.entities.TimeReport;
import com.example.EmployeeManagementSystem.repositories.TimeReportRepository;
import org.springframework.stereotype.Service;

@Service
public class TimeReportService {
    
    
    private final TimeReportRepository timeReportRepository;

    public TimeReportService(TimeReportRepository timeReportRepository) {
        this.timeReportRepository = timeReportRepository;
    }
    
    public void saveTimeReport(TimeReport timeReport){
        timeReportRepository.save(timeReport);
    }
    
    //TODO: Connection for timereport and employee
    
}
