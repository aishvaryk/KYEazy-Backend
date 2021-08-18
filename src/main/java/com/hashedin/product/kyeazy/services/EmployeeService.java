package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    public ActionDTO submit(Employee employee)
    {
        employeeRepository.save(employee);
        return null;
    }
    public ActionDTO updateProfile(Employee employeeDetails)
    {   Employee employee=employeeRepository.findById(employeeDetails.getEmployeeId()).get();
        employee.setAddress(employeeDetails.getAddress());
        employee.setContactNumber(employeeDetails.getContactNumber());
        employee.setName(employeeDetails.getName());
        employeeRepository.save(employee);
        return null;
    }

}
