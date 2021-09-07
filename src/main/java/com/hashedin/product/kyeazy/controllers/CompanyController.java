package com.hashedin.product.kyeazy.controllers;
import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.DataAlreadyExistsException;
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

@CrossOrigin
@RestController
@RequestMapping("/company")
public class CompanyController {

    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // company

    @PostMapping(value = "/register")
    public CompanyDTO register(@RequestBody Company company) {
        return companyService.register(company);
    }

    @PostMapping(value = "/register-employee/{companyId}")
    public ActionDTO registerEmployee(@RequestBody Employee employee, @PathVariable Integer companyId) throws Exception {
        return this.companyService.registerEmployee(employee, companyId);
    }

    @PostMapping(value = "/register-employees/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO registerEmployees(@PathVariable Integer id, @RequestParam("employeeCSV") MultipartFile employeeVideo) throws Exception {
        return companyService.registerEmployees(id, employeeVideo);
    }

    @PostMapping("/report-employee/{id}")
    public ActionDTO reportEmployee(@PathVariable Integer id, @RequestBody String message) {
        return companyService.reportEmployee(id,message);
    }

    @GetMapping("/re-kyc/{id}")
    public EmployeeDTO reKycEmployee(@PathVariable Integer id) {
        return companyService.reKycEmployee(id);
    }

    @PatchMapping(value="/add-icon/{id}",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO updateCompanyIcon(@PathVariable Integer id, @RequestParam("companyIcon") MultipartFile companyIcon) throws IOException
    {
        return  companyService.updateCompanyIcon(id,companyIcon);
    }

    @GetMapping("/get-company-details/{id}")
    public CompanyDTO getCompanyDetails(@PathVariable Integer id) {
        return companyService.getCompanyDetails(id);
    }

    // employees

    @GetMapping("/employees/{companyId}")
    public List<EmployeeDTO> getEmployees(@PathVariable Integer companyId, @RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String sort, @RequestParam String filter) {
        return companyService.getEmployees(companyId, pageNumber, pageSize,sort,filter);
    }

    @GetMapping("/get-employees-by-name/{id}/{name}")
    public List<EmployeeDTO> getEmployeesByName(@PathVariable Integer id, @PathVariable String name, @RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam String sort, @RequestParam String filter) {
        return companyService.getEmployeesByName(id, name, pageNumber, pageSize, sort, filter);
    }

    // count

    @GetMapping("/get-searched-employees-size/{companyId}/{name}/{status}")
    public long getSearchedEmployeesSize(@PathVariable String name, @PathVariable int companyId, @PathVariable String status ) {
        return companyService.getSearchedEmployeesSize(companyId, name, status);
    }

    @GetMapping("/get-employees-size/{companyId}/{filter}")
    public long getEmployeesSize(@PathVariable int companyId,@PathVariable String filter ) {
        return companyService.getEmployeesSize(companyId, filter);
    }


    // exceptions

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