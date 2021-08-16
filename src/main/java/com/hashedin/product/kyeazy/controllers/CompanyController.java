package com.hashedin.product.kyeazy.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kyc/company")
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
    @RequestMapping("/registerEmployee")
    public ActionDTO registerEmployee()
    {
        return null;
    }

    @RequestMapping("/getEmployees")
    public ActionDTO getEmployees()
    {
        return null;
    }
    @RequestMapping("/getEmployeesByStatus")
    public ActionDTO getEmployeesByStatus()
    {
        return null;
    }
    @RequestMapping("/viewProfile")
    public ActionDTO viewProfile()
    {
        return null;
    }
    @RequestMapping("/updateProfile")
    public ActionDTO updateProfile()
    {
        return null;
    }



}
