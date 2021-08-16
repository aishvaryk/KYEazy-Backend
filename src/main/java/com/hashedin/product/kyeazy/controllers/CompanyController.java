package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {

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
