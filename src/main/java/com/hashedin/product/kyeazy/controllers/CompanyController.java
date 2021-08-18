package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.RequiredFieldException;
import com.hashedin.product.kyeazy.exceptions.UserNotFoundException;
import com.hashedin.product.kyeazy.exceptions.response.ExceptionResponse;
import com.hashedin.product.kyeazy.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping("/login")
    public ActionDTO login() {
        return null;
    }

    @RequestMapping("/logout")
    public ActionDTO logout() {
        return null;
    }

    @PostMapping(value = "/register")
    public ActionDTO register(@RequestBody Company company) {
        return companyService.register(company);
    }

    @PostMapping(value = "/register-employee/{companyId}")
    public ActionDTO registerEmployee(@RequestBody Employee employee, @PathVariable Integer companyId) {
        return this.companyService.registerEmployee(employee, companyId);
    }

    @GetMapping("/employees/{companyId}")
    public Set<Employee> getEmployees(@PathVariable Integer companyId) {
        return companyService.getEmployees(companyId);
    }

    //error
    @GetMapping("/employees-by-status/{companyId}/{status}")
    public Set<Employee> getEmployeesByStatus(@PathVariable Integer companyId, @PathVariable String status) {
        return companyService.getEmployeesByStatus(companyId, status);
    }

    @GetMapping("/get-company-details")
    public ActionDTO getCompanyDetails() {
        return null;
    }

    @GetMapping("/get-employees-sorted-by-name")
    public ActionDTO getEmployeesSortedByName() {
        return null;
    }

    @RequestMapping("/get-employees-by-name")
    public ActionDTO getEmployeeByName() {
        return null;
    }


    @RequestMapping("/profile")
    public ActionDTO viewProfile() {
        return null;
    }

    @PatchMapping("/update-profile")
    public ActionDTO updateProfile(@RequestBody Company company) {
        return companyService.updateCompanyProfile(company);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(RequiredFieldException exc) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.LENGTH_REQUIRED.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.LENGTH_REQUIRED);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(UserNotFoundException exc) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.LENGTH_REQUIRED.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.LENGTH_REQUIRED);

    }
}