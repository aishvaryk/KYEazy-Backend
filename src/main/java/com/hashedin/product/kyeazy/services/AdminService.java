package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Transactional
    public List<EmployeeDTO> viewAllApplications(Integer pageNumber, Integer pageSize) {
        List<EmployeeDTO> employees = new LinkedList<>();
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        for (Employee employee : employeeRepository.findAll(pageable).getContent()) {
            EmployeeDTO employeeDTO = parseEmployee(employee);
            employees.add(employeeDTO);
        }
        return employees;
    }

    public EmployeeDTO viewEmployeeApplication(Integer employeeId) {

        EmployeeDTO employeeDTO;
        Employee employee = getEmployeeById(employeeId);
        return parseEmployee(employee);
    }

    @Transactional
    public List<EmployeeDTO> getEmployeesByCompanyId(Integer id, Integer pageNumber, Integer pageSize) {

        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<EmployeeDTO> employeeDTOS = new LinkedList<>();
        for (Employee employee : employeeRepository.findAllByCompanyId(id, pageable)) {
            employeeDTOS.add(parseEmployee(employee));
        }
        return employeeDTOS;
    }

    @Transactional
    public List<CompanyDTO> getCompanies(Integer pageNumber, Integer pageSize) {
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<CompanyDTO> companyDTOS = new LinkedList<>();
        for (Company company : companyRepository.findAll(pageable).getContent()) {
            companyDTOS.add(parseCompany(company));
        }
        return companyDTOS;
    }

    @Transactional
    public List<EmployeeDTO> viewAllApplicantsByName(Integer companyId, String name, Integer pageNumber, Integer pageSize) {

        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<EmployeeDTO> employeeDTOS = new LinkedList<>();
        for (Employee employee : employeeRepository.findAllByDisplayNameStartingWithAndCompanyId(name, companyId, pageable)) {
            employeeDTOS.add(parseEmployee(employee));
        }
        return employeeDTOS;
    }

    public EmployeeDTO verify(String status, Integer id) {

        Employee employee = getEmployeeById(id);
        employee.setStatus(status);
        employee.setDateTimeOfVerification(new Date());
        Employee savedEmployee = employeeRepository.save(employee);
        return parseEmployee(savedEmployee);
    }

    private Employee getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    @Transactional
    public List<CompanyDTO> getCompaniesByName(String name, Integer pageNumber, Integer pageSize) {
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<CompanyDTO> companyDTOS = new LinkedList<>();

        for (Company company : companyRepository.findAllCompaniesByNameStartingWith(name, pageable)) {
            companyDTOS.add(parseCompany(company));
        }

        return companyDTOS;
    }


    @Transactional
    public List<EmployeeDTO> getAllEmployeesSortedByName(Integer id, Integer pageNumber, Integer pageSize) {
        List<EmployeeDTO> allEmployeeDTOS = new LinkedList<>();
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("displayName"));

        for (Employee e : employeeRepository.findAllByCompanyId(id, pageable)) {
            allEmployeeDTOS.add(parseEmployee(e));
        }
        return allEmployeeDTOS;
    }

    @Transactional
    public List<EmployeeDTO> getAllEmployeesSortedByDate(Integer id, Integer pageNumber, Integer pageSize) {

        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateTimeOfApplication").descending());

        List<EmployeeDTO> allEmployeeDTOS = new LinkedList<>();
        for (Employee e : employeeRepository.findAllByCompanyId(id, pageable)) {
            allEmployeeDTOS.add(parseEmployee(e));
        }
        return allEmployeeDTOS;

    }

    @Transactional
    public List<EmployeeDTO> getEmployeesByStatus(Integer companyId, String status, Integer pageNumber, Integer pageSize) {
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateTimeOfApplication").descending());

        List<EmployeeDTO> allEmployeeDTOS = new LinkedList<>();
        for (Employee e : employeeRepository.findAllByStatusAndCompanyId(status, companyId, pageable)) {
            allEmployeeDTOS.add(parseEmployee(e));
        }
        return allEmployeeDTOS;
    }

    @Transactional
    public int getTotalNumberOfEmployees() {
        return employeeRepository.findAll().size();
    }

    @Transactional
    public int getTotalNumberOfAcceptedEmployees() {
        return employeeRepository.findAllEmployeesByStatus("Accepted").size();
    }

    @Transactional
    public int getTotalNumberOfPendingEmployees() {
        return employeeRepository.findAllEmployeesByStatus("Pending").size();
    }

    @Transactional
    public int getTotalNumberOfRegisteredEmployees() {
        return employeeRepository.findAllEmployeesByStatus("Registered").size();
    }

    @Transactional
    public int getTotalNumberOfRejectEmployees() {
        return employeeRepository.findAllEmployeesByStatus("Rejected").size();
    }

    public ResponseEntity<byte[]> getVideo(String username) throws IOException {

        System.out.println(username);
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/employee_videos/" + username + ".mp4"));
        System.out.println(bytes.length);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "video/mp4");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);

    }

    public ResponseEntity<byte[]> getDocument(String username) throws IOException {

        System.out.println(username);
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/employee_documents/" + username + ".mp4"));
        System.out.println(bytes.length);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/pdf");
        headers.setContentLength(bytes.length);
        return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);

    }

    @Transactional
    private CompanyDTO parseCompany(Company company) {

        CompanyDTO companyDTO = new CompanyDTO();
        List<EmployeeDTO> employeeDTOS = new LinkedList<>();
        EmployeeDTO employeeDTO;
        for (Employee employee : company.getEmployees()) {
            employeeDTO = parseEmployee(employee);
            employeeDTOS.add(employeeDTO);
        }
        companyDTO.setEmployees(employeeDTOS);
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setCompanyDescription(company.getCompanyDescription());
        companyDTO.setName(company.getName());
        companyDTO.setCinNumber(company.getCinNumber());
        companyDTO.setUsername(company.getUsername());
        companyDTO.setAddress(companyDTO.getAddress());
        companyDTO.setIcon(company.getIcon());
        return companyDTO;
    }

    private EmployeeDTO parseEmployee(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setUsername(employee.getUsername());
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setContactNumber(employee.getContactNumber());
        employeeDTO.setEmailID(employee.getEmailID());
        employeeDTO.setDateTimeOfApplication(employee.getDateTimeOfApplication());
        employeeDTO.setDateTimeOfVerification(employee.getDateTimeOfVerification());
        employeeDTO.setDocumentNumber(employee.getDocumentNumber());
        employeeDTO.setCompanyId(employee.getCompanyId());
        employeeDTO.setDocumentType(employee.getDocumentType());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setGender(employee.getGender());
        employeeDTO.setCapturedImage(employee.getCapturedImage());
        employeeDTO.setGender(employee.getGender());
        return employeeDTO;
    }

}