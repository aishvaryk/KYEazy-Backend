package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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

    @RequestMapping("/view-all-applications")
    public List<Employee> viewAllApplications()
    {
        return adminService.viewAllApplications();
    }

    @RequestMapping("/view-pending-applications")
    public List<Employee> viewPendingApplications()
    {
        return adminService.viewPendingApplications();
    }

    @RequestMapping("/view-accepted-applications")
    public List<Employee> viewAcceptedApplications()
    {
        return adminService.viewAcceptedApplications();
    }

    @RequestMapping("/view-rejected-applications")
    public List<Employee> viewRejectedApplications()
    {
        return adminService.viewRejectedApplications();
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
