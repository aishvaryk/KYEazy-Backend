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
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // all-companies

    @GetMapping("/companies")
    public List<CompanyDTO> getCompanies(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return adminService.getCompanies(pageNumber, pageSize);
    }

    @RequestMapping("/get-all-companies-by-name/{name}")
    public List<CompanyDTO> viewAllCompaniesByName(@PathVariable String name, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return adminService.getCompaniesByName(name, pageNumber, pageSize);
    }

    // employees

    @RequestMapping("/view-all-applications")
    public List<EmployeeDTO> viewAllApplications(
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam String sort,
            @RequestParam String filter) {
        return adminService.viewAllApplications(pageNumber, pageSize, sort, filter);
    }

    @RequestMapping("/get-all-employees-by-name/{name}")
    public List<EmployeeDTO> viewAllApplicantsByName(
            @PathVariable String name,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize,
            @RequestParam String sort,
            @RequestParam String filter

    ){
        return adminService.viewAllApplicantsByName( name, pageNumber, pageSize, sort, filter);
    }

    // employee

    @RequestMapping("/verify/{id}/{status}")
    public EmployeeDTO verify(@PathVariable Integer id, @PathVariable String status) {
        return adminService.verify(status, id);
    }

    @RequestMapping("/view-employee-details/{employeeId}")
    public EmployeeDTO viewEmployeeDetails(@PathVariable Integer employeeId) {
        return adminService.viewEmployeeApplication(employeeId);
    }

    @RequestMapping("/get-video/{username}")
    public ResponseEntity<byte[]> getVideo(@PathVariable String username) throws IOException {
        return adminService.getVideo(username);
    }

    @RequestMapping("/get-document/{username}")
    public ResponseEntity<byte[]> getDocument(@PathVariable String username) throws IOException {
        return adminService.getDocument(username);
    }

    // company-count

    @GetMapping("/get-total-number-of-companies")
    public long getTotalNumberOfCompanies() {
        return adminService.getTotalNumberOfCompanies();
    }

    @GetMapping("/get-searched-companies-size/{name}")
    public long getSearchedCompaniesSize(@PathVariable String name) {
        return adminService.getSearchedCompaniesSize(name);
    }

    // employee-count

    @GetMapping("/get-number-of-employees")
    public long getTotalNumberOfEmployees() {
        return adminService.getTotalNumberOfEmployees();
    }

    @GetMapping("/get-number-of-rejected-employees")
    public Integer getTotalNumberOfRejectEmployees() {
        return adminService.getTotalNumberOfRejectEmployees();
    }

    @GetMapping("/get-number-of-accepted-employees")
    public Integer getTotalNumberOfAcceptedEmployees() {
        return adminService.getTotalNumberOfAcceptedEmployees();
    }

    @GetMapping("/get-number-of-pending-employees")
    public Integer getTotalNumberOfPendingEmployees() {
        return adminService.getTotalNumberOfPendingEmployees();
    }

    @GetMapping("/get-number-of-registered-employees")
    public Integer getTotalNumberOfRegisteredEmployees() {
        return adminService.getTotalNumberOfRegisteredEmployees();
    }

    @GetMapping("/get-employees-size/{status}")
    public Long getEmployeesSize(@PathVariable String status) {
        return adminService.getEmployeeSize(status);
    }

    @GetMapping("/get-searched-employees-size/{name}/{status}")
    public long getSearchedEmployeesSize(@PathVariable String name, @PathVariable String status) {
        return adminService.getSearchedEmployeeSize(name, status);
    }

    // exceptions

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception exc) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.LENGTH_REQUIRED.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.LENGTH_REQUIRED);
    }

}
