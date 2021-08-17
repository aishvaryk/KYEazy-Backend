package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

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
        Integer id=companyDetails.getCompanyId();
        Company company=companyRepository.findById(id).get();
        company.setCompanyDescription(companyDetails.getCompanyDescription());
        company.setAddress(companyDetails.getAddress());
        company.setEmployees(company.getEmployees());
        company.setName(company.getName());
        companyRepository.save(company);
        return null;
    }
}
