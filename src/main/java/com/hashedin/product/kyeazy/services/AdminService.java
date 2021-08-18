package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

public class AdminService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepo;

    public Set<Employee> viewAllApplications(Integer id)
    {
        Company company= companyRepo.getById(id);
        Set<Employee> employee=company.getEmployees();
        return employee;
    }
    public Set<Employee> viewPendingApplications(Integer id)
    {
        Company company= companyRepo.getById(id);
        Set<Employee> employee=company.getEmployees();
        Set<Employee> pendingApplications = null;
        for(Employee e:employee){
            if(e.getStatus().equals("pending"))
                pendingApplications.add(e);
        }
        return pendingApplications;
    }
    public Set<Employee> viewAcceptedApplications(Integer id)
    {
        Company company= companyRepo.getById(id);
        Set<Employee> employee=company.getEmployees();
        Set<Employee> acceptedApplications = null;
        for(Employee e:employee){
            if(e.getStatus().equals("accepted"))
                acceptedApplications.add(e);
        }
        return acceptedApplications;
    }
    public Set<Employee> viewRejectedApplications(Integer id)
    {
        Company company= companyRepo.getById(id);
        Set<Employee> employee=company.getEmployees();
        Set<Employee> rejectedApplications = null;
        for(Employee e:employee){
            if(e.getStatus().equals("rejected"))
                rejectedApplications.add(e);
        }
        return rejectedApplications;
    }
    public void verify(String status,Integer id){
        Employee employee=employeeRepository.findById(id).get();
        employee.setStatus(status);
        employeeRepository.save(employee);
    }
}
