package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService)
    {
        this.companyService=companyService;
    }

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

    @PostMapping(value="/register")
    public ActionDTO register(@RequestBody Company company)
    {
        return companyService.register(company);
    }

    @PostMapping(value="/register-employee")
    public ActionDTO registerEmployee(@RequestBody Employee employee)
    {
        return this.companyService.registerEmployee(employee);
    }
    @GetMapping(value="/mailTest")
    public String mailtest()

    {
        Employee employee=new Employee();
        employee.setFirstName("Riya");
        employee.setLastName("Punjabi");
        employee.setEmailID("riyapunjabi2019@gmail.com");
         this.companyService.registerEmployee(employee);
         return "Riya";
    }

    @RequestMapping("/employees")
    public ActionDTO getEmployees()
    {
        return null;
    }

    @RequestMapping("/employees-by-status")
    public ActionDTO getEmployeesByStatus()
    {
        return null;
    }

    @RequestMapping("/profile")
    public ActionDTO viewProfile()
    {
        return null;
    }

    @RequestMapping("/update-profile")
    public ActionDTO updateProfile()
    {
        return null;
    }

}
