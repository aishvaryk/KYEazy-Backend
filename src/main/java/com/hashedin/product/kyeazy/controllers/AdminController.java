package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
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
    public List<EmployeeDTO> viewAllApplications()
    {
        return adminService.viewAllApplications();
    }

    @RequestMapping("/view-pending-applications")
    public Set<EmployeeDTO> viewPendingApplications()
    {
        return adminService.viewPendingApplications();
    }

    @RequestMapping("/view-accepted-applications")
    public Set<EmployeeDTO> viewAcceptedApplications()
    {
        return adminService.viewAcceptedApplications();
    }

    @RequestMapping("/view-rejected-applications")
    public Set<EmployeeDTO> viewRejectedApplications()
    {
        return adminService.viewRejectedApplications();
    }

    @RequestMapping("/viewEmployeeDetails/{id}")
    public EmployeeDTO viewEmployeeDetails(@PathVariable Integer id)
    {
        return adminService.viewEmployeeApplication(id);
    }

    @GetMapping(value="/{id}/profile-picture")
    public byte[] getEmployeeImage(@PathVariable Integer employeeId) throws IOException {
        return adminService.getEmployeeImage(employeeId);
    }


    @RequestMapping("/verify/{id}/{status}")
    public EmployeeDTO verify(@PathVariable Integer id,@PathVariable String status)
    {
        return adminService.verify(status,id);
    }


}
