package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
@Service
public class AdminService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepo;

    public List<Employee> viewAllApplications()
    {
        List<Employee> employee=employeeRepository.findAll();
        return employee;
    }
    public List<Employee> viewPendingApplications()
    {
        List<Employee> employee=employeeRepository.findAll();
        List<Employee> pendingApplications = null;
        for(Employee e:employee){
            if(e.getStatus().equals("pending"))
                pendingApplications.add(e);
        }
        return pendingApplications;
    }
    public List<Employee> viewAcceptedApplications()
    {
        List<Employee> employee=employeeRepository.findAll();
        List<Employee> acceptedApplications = null;
        for(Employee e:employee){
            if(e.getStatus().equals("accepted"))
                acceptedApplications.add(e);
        }
        return acceptedApplications;
    }
    public List<Employee> viewRejectedApplications()
    {
        List<Employee> employee=employeeRepository.findAll();
        List<Employee> rejectedApplications = null;
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
