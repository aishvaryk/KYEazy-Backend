package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
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
    public List<EmployeeDTO> viewAllApplications(@RequestParam Integer pageNumber,@RequestParam Integer pageSize)
    {
        return adminService.viewAllApplications(pageNumber,pageSize);
    }

    @RequestMapping("/view-pending-applications")
    public Set<EmployeeDTO> viewPendingApplications(@RequestParam Integer pageNumber,@RequestParam Integer pageSize)
    {
        return adminService.viewPendingApplications(pageNumber,pageSize);
    }

    @RequestMapping("/view-accepted-applications")
    public Set<EmployeeDTO> viewAcceptedApplications(@RequestParam Integer pageNumber,@RequestParam Integer pageSize)
    {
        return adminService.viewAcceptedApplications(pageNumber,pageSize);
    }

    @RequestMapping("/view-rejected-applications")
    public Set<EmployeeDTO> viewRejectedApplications(@RequestParam Integer pageNumber,@RequestParam Integer pageSize)
    {
        return adminService.viewRejectedApplications(pageNumber,pageSize);
    }

    @RequestMapping("/view-employee-details/{id}")
    public EmployeeDTO viewEmployeeDetails(@PathVariable Integer id)
    {
        return adminService.viewEmployeeApplication(id);
    }
/*
    @GetMapping(value="/profile-picture/{employeeId}")
    public byte[] getEmployeeImage(@PathVariable Integer employeeId) throws IOException {
        return adminService.getEmployeeImage(employeeId);
    }
*/

    @RequestMapping("/verify/{id}/{status}")
    public EmployeeDTO verify(@PathVariable Integer id,@PathVariable String status)
    {
        return adminService.verify(status,id);
    }


}
