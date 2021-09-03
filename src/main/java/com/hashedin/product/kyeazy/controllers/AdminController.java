package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.exceptions.response.ExceptionResponse;
import com.hashedin.product.kyeazy.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

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

    @RequestMapping("/view-all-applications")
    public List<EmployeeDTO> viewAllApplications(@RequestParam Integer pageNumber,@RequestParam Integer pageSize)
    {
        return adminService.viewAllApplications(pageNumber,pageSize);
    }

    @RequestMapping("/view-employee-details/{employeeId}")
    public EmployeeDTO viewEmployeeDetails(@PathVariable Integer employeeId)
    {
        return adminService.viewEmployeeApplication(employeeId);
    }

    @GetMapping("/employees/{companyId}")
    public List<EmployeeDTO> getCompanyEmployees(@PathVariable Integer companyId,@RequestParam Integer pageNumber,@RequestParam Integer pageSize)
    {
    return adminService.getEmployeesByCompanyId(companyId,pageNumber,pageSize);
    }

    @GetMapping("/companies")
    public List<CompanyDTO> getCompanies(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return adminService.getCompanies(pageNumber,pageSize);
    }

    @RequestMapping("/get-all-employees-by-name/{id}/{name}")
    public List<EmployeeDTO> viewAllApplicantsByName(@PathVariable Integer id,@PathVariable String name,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return adminService.viewAllApplicantsByName(id,name,pageNumber,pageSize);
    }

    @RequestMapping("/get-all-companies-by-name/{name}")
    public List<CompanyDTO> viewAllCompaniesByName(@PathVariable String name,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return adminService.getCompaniesByName(name,pageNumber,pageSize);
    }

    @RequestMapping("/get-all-employees-sorted-by-name/{id}")
    public List<EmployeeDTO> getAllEmployeesSortedByName(@PathVariable  Integer id,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return adminService.getAllEmployeesSortedByName(id,pageNumber,pageSize);
    }

    @RequestMapping("/get-all-employees-sorted-by-date/{id}")
    public List<EmployeeDTO> getAllEmployeesSortedByDate(@PathVariable Integer id,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return adminService.getAllEmployeesSortedByDate(id,pageNumber,pageSize);
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
    @RequestMapping("/get-document/{username}")
    public ResponseEntity<byte[]> getDocument(@PathVariable String username) throws IOException
    {
        return adminService.getDocument(username);
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

    @GetMapping("/employees-by-status/{companyId}/{status}")
    public List<EmployeeDTO> getEmployeesByStatus(@PathVariable Integer companyId, @PathVariable String status,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return adminService.getEmployeesByStatus(companyId, status,pageNumber,pageSize);
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
