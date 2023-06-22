
package com.example.EmployeeManagementSystem.services;

import com.example.EmployeeManagementSystem.entities.Employee;
import com.example.EmployeeManagementSystem.entities.TimeReport;
import com.example.EmployeeManagementSystem.repositories.TimeReportRepository;
import java.time.LocalDate;
import java.time.YearMonth;

import java.util.List;
import java.util.stream.Collectors;

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
    
    
    public TimeReport getTimeReportById(int id){
        return timeReportRepository.findById(id).get();
    }
 
    public List<TimeReport> getEmployeesTimeReportByCurrentMonth(Employee employee){
        
        List<TimeReport> timeReports = employee.getTimeReports();
        
        List<TimeReport> filteredReports = timeReports.stream()
            .filter(report -> {
                
                LocalDate reportDate = LocalDate.parse(report.getDate());
                YearMonth month = YearMonth.from(reportDate);
                
                return month.equals(YearMonth.now());
            }).collect(Collectors.toList());
           
        return filteredReports;
    }
    
    public void deleteEmployeesTimeReports(List<TimeReport> timeReports){
        for (TimeReport timeReport : timeReports) {
            timeReportRepository.delete(timeReport);
        }
        
    }
    
}
