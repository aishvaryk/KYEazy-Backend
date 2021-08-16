package com.hashedin.product.kyeazy.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/employee")
public class AdminController {
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
    @RequestMapping("/viewAllApplications")
    public ActionDTO viewAllApplications()
    {
        return null;
    }
    @RequestMapping("/viewPendingApplications")
    public ActionDTO viewPendingApplications()
    {
        return null;
    }
    @RequestMapping("/viewAcceptedApplications")
    public ActionDTO viewAcceptedApplications()
    {
        return null;
    }
    @RequestMapping("/viewRejectedApplications")
    public ActionDTO viewRejectedApplications()
    {
        return null;
    }
    @RequestMapping("/verify")
    public ActionDTO verify()
    {
        return null;
    }

}
