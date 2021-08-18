package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

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

    @RequestMapping("/register")
    public ActionDTO register()
    {
        return null;
    }

    @RequestMapping("/register-employee")
    public ActionDTO registerEmployee()
    {
        return null;
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

    @GetMapping("/get-company-details")
    public ActionDTO getCompanyDetails()
    {
        return null;
    }

    @GetMapping("/get-employees-sortedbyname")
    public ActionDTO getEmployeesSortedByName()
    {
        return null;
    }

    @RequestMapping("/get-employees-byname")
    public ActionDTO getEmployeeByName()
    {
        return null;
    }


    @RequestMapping("/profile")
    public ActionDTO viewProfile()
    {
        return null;
    }

    @PutMapping("/update-profile")
    public ActionDTO updateProfile(@RequestBody Company company)
    {
        return companyService.updateProfile(company);
    }

}
