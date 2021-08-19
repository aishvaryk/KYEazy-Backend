package com.hashedin.product.kyeazy.services;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;

    public Employee viewEmployeeApplication(Integer employeeId)
    {
     return employeeRepository.findById(employeeId).get();
    }

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
            if(e.getStatus().equalsIgnoreCase("accepted"))
                acceptedApplications.add(e);
        }
        return acceptedApplications;
    }
    public List<Employee> viewRejectedApplications()
    {
        List<Employee> employee=employeeRepository.findAll();
        List<Employee> rejectedApplications = null;
        for(Employee e:employee){
            if(e.getStatus().equalsIgnoreCase("rejected"))
                rejectedApplications.add(e);
        }
        return rejectedApplications;
    }
    public void verify(String status,Integer id){
        Employee employee=getEmployeeById(id);
        employee.setStatus(status);
        employeeRepository.save(employee);
    }
    public byte[] getEmployeeImage(Integer employeeId) {
        Employee employee=getEmployeeById(employeeId);
        byte[] profilePictureBytes = employee.getCapturedImage();
        return profilePictureBytes;
    }
    public Employee getEmployeeById(Integer employeeId)
    {
        return employeeRepository.findById(employeeId).get();
    }

}
