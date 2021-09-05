package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.DataAlreadyExistsException;
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

    @PostMapping(value = "/register")
    public CompanyDTO register(@RequestBody Company company) {
        return companyService.register(company);
    }

    @PostMapping(value = "/register-employees/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO registerEmployees(@PathVariable Integer id, @RequestParam("employeeCSV") MultipartFile employeeVideo) throws IOException {
        return companyService.registerEmployees(id, employeeVideo);
    }

    @PostMapping(value = "/register-employee/{companyId}")
    public ActionDTO registerEmployee(@RequestBody Employee employee, @PathVariable Integer companyId) {
        return this.companyService.registerEmployee(employee, companyId);
    }

    @GetMapping("/employees/{companyId}")
    public List<EmployeeDTO> getEmployees(@PathVariable Integer companyId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return companyService.getEmployees(companyId, pageNumber, pageSize);
    }

    //error
    @GetMapping("/employees-by-status/{companyId}/{status}")
    public List<EmployeeDTO> getEmployeesByStatus(@PathVariable Integer companyId, @PathVariable String status, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return companyService.getEmployeesByStatus(companyId, status, pageNumber, pageSize);
    }

    @GetMapping("/get-company-details/{id}")
    public CompanyDTO getCompanyDetails(@PathVariable Integer id) {
        return companyService.getCompanyDetails(id);
    }

    @GetMapping("/get-employees-sorted-by-name/{id}")
    public List<EmployeeDTO> getEmployeesSortedByName(@PathVariable Integer id, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return companyService.getEmployeesSortedByName(id, pageNumber, pageSize);
    }

    @GetMapping("/get-employees-sorted-by-date/{id}")
    public List<EmployeeDTO> getEmployeesSortedByDate(@PathVariable Integer id, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return companyService.getEmployeesSortedByDate(id, pageNumber, pageSize);
    }



    @GetMapping("/get-employees-by-name/{id}/{name}")
    public List<EmployeeDTO> getEmployeeByName(@PathVariable Integer id, @PathVariable String name, @RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return companyService.getEmployeeByName(id, name, pageNumber, pageSize);
    }
    @PostMapping("/report-employee/{id}")
    public ActionDTO reportEmployee(@PathVariable Integer id, @RequestBody String message) {
        return companyService.reportEmployee(id,message);
    }
    @PatchMapping(value="/add-icon/{id}",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO updateCapturedImage(@PathVariable Integer id, @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException
    {
        return  companyService.updateCompanyImage(id,profilePicture);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(DataAlreadyExistsException exc) {
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