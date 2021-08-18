package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/login")
    public ActionDTO login()
    {
        return null;
    }

    @RequestMapping("/logout")
    public ActionDTO logout()
    {
        return null;
    }

    @RequestMapping("/submit")
    public ActionDTO submit(Employee employee)
    {
        return employeeService.submit(employee);
    }

    @RequestMapping("/update-profile")
    public ActionDTO updateProfile(Employee employee)
    {
        return  employeeService.updateProfile(employee);
    }
}
