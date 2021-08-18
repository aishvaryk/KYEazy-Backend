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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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

    @GetMapping("/get-company-details/{id}")
    public Company getCompanyDetails(@PathVariable Integer id) {
        return companyService.getCompanyDetails(id);
    }

    @GetMapping("/get-employees-sorted-by-name")
    public List<Employee> getEmployeesSortedByName() {
        return companyService.getEmployeesSortedByName();
    }

    @GetMapping ("/get-employees-by-name/{id}/{name}")
    public Employee getEmployeeByName(@PathVariable Integer id, @PathVariable String name) {
        return companyService.getEmployeeByName(id,name);
    }

    @GetMapping ("/get-employees-with-pending-kyc/{id}")
    public List<Employee> getEmployeesWithPendingKYC(@PathVariable Integer id){
        return companyService.getEmployeesWithPendingKYC(id);
    }

    @GetMapping ("/get-employees-with-rejected-kyc/{id}")
    public List<Employee> getEmployeesWithRejectedKYC(@PathVariable Integer id){
        return companyService.getEmployeesWithRejectedKYC(id);
    }

    @GetMapping ("/get-employees-by-date-of-application/{date}")
    public List<Employee> getEmployeesByDateOfApplication(@PathVariable String date){
        //Instant timestamp = null;
        //LocalDateTime dateTime = LocalDateTime.parse("2018-05-05T11:50:55");
        return companyService.getEmployeesByDateOfApplication(date);
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