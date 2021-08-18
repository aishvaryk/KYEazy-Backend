package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Service
public class CompanyService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    public Set<Employee> getEmployees(Integer id)
    {
        Company company= companyRepository.getById(id);
        return company.getEmployees();
    }
    public Set<Employee> getEmployeesByStatus(Integer id,String status){
        Company company= companyRepository.getById(id);
        Set<Employee> employee=company.getEmployees();
        Set<Employee> employeesByStatus=null;
        for(Employee e:employee){
            if(e.getStatus().equals(status))
                employeesByStatus.add(e);
        }
        return  employeesByStatus;
    }

    public ActionDTO updateProfile(Company companyDetails)
    {
        companyRepository.save(companyDetails);
        return null;
    }

    public ActionDTO getCompanyDetails(Integer id)
    {
        Company company=companyRepository.findById(id).get();
        return new ActionDTO();
    }
    public List<Employee> getEmployeeByName(Integer companyId,String name)
    {   Company company=companyRepository.findById(companyId).get();
        Set<Employee> employeeList=company.getEmployees();
        List<Employee> employees=null;
        for(Employee e:employeeList){
            if(e.getName().equals(name))
                employees.add(e);
        }
        return  employees;
    }

    public List<Employee> getEmployeesSortedByName()
    {
        List<Employee> employee=employeeRepository.findAll();
        employee.sort(new ComparatorService());
        return  employee;
    }

    public List<Employee> getEmployeesWithPendingKYC(Integer id)
    {
        Company company=companyRepository.findById(id).get();
        Set<Employee> employee=company.getEmployees();
        List<Employee> pendingEmployees = null;
        for(Employee e:employee){
            if(e.getStatus().equals("pending"))
                pendingEmployees.add(e);
        }
        return pendingEmployees;
    }
    public List<Employee> getEmployeesWithRejectedKYC(Integer id)
    {
        Company company=companyRepository.findById(id).get();
        Set<Employee> employee=company.getEmployees();
        List<Employee> rejectedEmployees = null;
        for(Employee e:employee){
            if(e.getStatus().equals("rejected"))
                rejectedEmployees.add(e);
        }
        return rejectedEmployees;
    }

    public List<Employee> getEmployeesByDateOfApplication(Date date)
    {
        List<Employee> employeeList=employeeRepository.findAll();
        List<Employee> employees=null;
        for(Employee e:employeeList){
            if(e.getDateTimeOfVerification().equals(date))
                employees.add(e);
        }
        return  employees;
    }
}
