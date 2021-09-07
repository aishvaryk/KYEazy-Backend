package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println(userName.substring(0,1));
        if(userName.charAt(0)=='C') {
            Company company = companyService.getCompanyByUsername(userName.substring(1,userName.length()));
            return new User(company.getUsername(), company.getPassword(), new ArrayList<>());
        }
        if(userName.charAt(0)=='E') {
            Employee employee = employeeService.getEmployeeByUsername(userName.substring(1,userName.length()));
            return new User(employee.getUsername(), employee.getPassword(), new ArrayList<>());
        }
        if(userName.charAt(0)=='A') {
            return new User("Riya", "Riya123", new ArrayList<>());
        }
    return  null;

    }

}
