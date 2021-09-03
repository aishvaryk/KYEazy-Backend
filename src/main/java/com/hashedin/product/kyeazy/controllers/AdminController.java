package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.DataNotFoundException;
import com.hashedin.product.kyeazy.exceptions.response.ExceptionResponse;
import com.hashedin.product.kyeazy.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
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


    @GetMapping("/get-number-of-employee")
    public Integer getTotalNumberOfEmployees()
    {
        return adminService.getTotalNumberOfEmployees();
    }

    @GetMapping("/get-number-of-rejected-employee")
    public Integer getTotalNumberOfRejectEmployees()
    {
        return adminService.getTotalNumberOfRejectEmployees();
    }

    @GetMapping("/get-number-of-accepted-employee")
    public Integer getTotalNumberOfAcceptedEmployees()
    {
        return adminService.getTotalNumberOfAcceptedEmployees();
    }

    @GetMapping("/get-number-of-pending-employee")
    public Integer getTotalNumberOfPendingEmployees()
    {
        return adminService.getTotalNumberOfPendingEmployees();
    }

    @GetMapping("/get-number-of-registered-employee")
    public Integer getTotalNumberOfRegisteredEmployees()
    {
        return adminService.getTotalNumberOfRegisteredEmployees();
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
    @GetMapping("/employees/{companyId}")
    public List<EmployeeDTO> getEmployees(@PathVariable Integer companyId,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
    return adminService.getEmployees(companyId,pageNumber,pageSize);
    }

    @GetMapping("/companies")
    public List<CompanyDTO> getCompanies(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return adminService.getCompanies(pageNumber,pageSize);
    }
    @RequestMapping("/get-all-employees-by-name/{name}")
    public List<EmployeeDTO> viewAllApplicantsByName(@PathVariable String name,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return adminService.viewAllApplicantsByName(name,pageNumber,pageSize);
    }

    @RequestMapping("/get-all-employees-sorted-by-name")
    public Set<EmployeeDTO> getAllEmployeesSortedByName(@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return adminService.getAllEmployeesSortedByName(pageNumber,pageSize);
    }

    @RequestMapping("/get-all-employees-sorted-by-date")
    public Set<EmployeeDTO> getAllEmployeesSortedByDate(@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return adminService.getAllEmployeesSortedByDate(pageNumber,pageSize);
    }

    @RequestMapping("/verify/{id}/{status}")
    public EmployeeDTO verify(@PathVariable Integer id,@PathVariable String status)
    {
        return adminService.verify(status,id);
    }
    @RequestMapping("/get-video/{username}")
    public ResponseEntity<byte[]> getVideo(@PathVariable String username) throws IOException
    {
        return adminService.getVideo(username);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception exc) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.LENGTH_REQUIRED.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.LENGTH_REQUIRED);

    }


}
