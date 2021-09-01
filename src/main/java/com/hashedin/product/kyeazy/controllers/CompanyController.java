package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.DataNotFoundException;
import com.hashedin.product.kyeazy.exceptions.RequiredFieldException;
import com.hashedin.product.kyeazy.exceptions.response.ExceptionResponse;
import com.hashedin.product.kyeazy.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@CrossOrigin
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

    @PostMapping(value="/register-employees/{id}",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO registerEmployees(@PathVariable Integer id, @RequestParam("employeeCSV") MultipartFile employeeVideo) throws IOException
    {
        return  companyService.registerEmployees(id,employeeVideo);
    }

    @PostMapping(value = "/register-employee/{companyId}")
    public ActionDTO registerEmployee(@RequestBody Employee employee, @PathVariable Integer companyId) {
        return this.companyService.registerEmployee(employee, companyId);
    }


    @GetMapping("/employees/{companyId}")
    public List<EmployeeDTO> getEmployees(@PathVariable Integer companyId,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return companyService.getEmployees(companyId,pageNumber,pageSize);
    }

    //error
    @GetMapping("/employees-by-status/{companyId}/{status}")
    public Set<EmployeeDTO> getEmployeesByStatus(@PathVariable Integer companyId, @PathVariable String status,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return companyService.getEmployeesByStatus(companyId, status,pageNumber,pageSize);
    }

    @GetMapping("/get-company-details/{id}")
    public CompanyDTO getCompanyDetails(@PathVariable Integer id) {
        return companyService.getCompanyDetails(id);
    }

    @GetMapping("/get-employees-sorted-by-name/{id}")
    public Set<EmployeeDTO> getEmployeesSortedByName(@PathVariable Integer id,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return companyService.getEmployeesSortedByName(id,pageNumber,pageSize);
    }

    @GetMapping("/get-employees-sorted-by-date/{id}")
    public Set<EmployeeDTO> getEmployeesSortedByDate(@PathVariable Integer id,@RequestParam Integer pageNumber,@RequestParam Integer pageSize) {
        return companyService.getEmployeesSortedByDate(id,pageNumber,pageSize);
    }


    @GetMapping("/get-employees-by-name/{id}/{name}")
    public List<EmployeeDTO> getEmployeeByName(@PathVariable Integer id, @PathVariable String name) {
        return companyService.getEmployeeByName(id,name);
    }
/*
    @GetMapping ("/get-employees-with-pending-kyc/{id}")
    public Set<EmployeeDTO> getEmployeesWithPendingKYC(@PathVariable Integer id,@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        return companyService.getEmployeesWithPendingKYC(id,pageNumber,pageSize);
    }
*/
    @GetMapping ("/get-registered-employees/{id}")
    public Set<EmployeeDTO> getRegisteredEmployees(@PathVariable Integer id,@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        return companyService.getRegisteredEmployees(id,pageNumber,pageSize);
    }
/*
    @GetMapping ("/get-employees-with-rejected-kyc/{id}")
    public Set<EmployeeDTO> getEmployeesWithRejectedKYC(@PathVariable Integer id,@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        return companyService.getEmployeesWithRejectedKYC(id,pageNumber,pageSize);
    }
*/


    @GetMapping ("/get-employees-by-date-of-application/{date}")
    public Set<EmployeeDTO> getEmployeesByDateOfApplication(@PathVariable String date,@RequestParam Integer pageNumber,@RequestParam Integer pageSize){
        //Instant timestamp = null;
        //LocalDateTime dateTime = LocalDateTime.parse("2018-05-05T11:50:55");
        return companyService.getEmployeesByDateOfApplication(date,pageNumber,pageSize);
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
    public ResponseEntity<ExceptionResponse> handleException(DataNotFoundException exc) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.LENGTH_REQUIRED.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.LENGTH_REQUIRED);

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