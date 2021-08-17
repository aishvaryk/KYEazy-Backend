package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService)
    {
        this.adminService=adminService;
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

    @RequestMapping("/view-all-applications/{companyId}")
    public Set<Employee> viewAllApplications(@PathVariable Integer companyId)
    {
        return adminService.viewAllApplications(companyId);
    }

    @RequestMapping("/view-pending-applications/{companyId}")
    public Set<Employee> viewPendingApplications(@PathVariable Integer companyId)
    {
        return adminService.viewPendingApplications(companyId);
    }

    @RequestMapping("/view-accepted-applications/{companyId}")
    public Set<Employee> viewAcceptedApplications(@PathVariable Integer companyId)
    {
        return adminService.viewAcceptedApplications(companyId);
    }

    @RequestMapping("/view-rejected-applications/{companyId}")
    public Set<Employee> viewRejectedApplications(@PathVariable Integer companyId)
    {
        return adminService.viewRejectedApplications(companyId);
    }
    @RequestMapping("/viewEmployeeDetails")
    public ActionDTO viewEmployeeDetails()
    {
        return null;
    }

    @RequestMapping("/verify")
    public ActionDTO verify()
    {
        return null;
    }

}
