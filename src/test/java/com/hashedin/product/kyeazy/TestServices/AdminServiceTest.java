package com.hashedin.product.kyeazy.TestServices;

import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import com.hashedin.product.kyeazy.services.AdminService;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {
    @InjectMocks
    AdminService adminService;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    CompanyRepository companyRepository;

    @Test
    public void shouldVerify() {
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        employee.setStatus("Registered");

        when(employeeRepository.save(isA(Employee.class))).thenReturn(employee);
        when(employeeRepository.findById(isA(Integer.class))).thenReturn(java.util.Optional.of(employee));
        EmployeeDTO employeeDTO= adminService.verify("Accepted",1);
        assertEquals(employeeDTO.getStatus(),"Accepted");
    }

    @Test
    public  void shouldGetCompaniesByName() {
        Company company= new Company();
        company.setCompanyId(1);
        company.setName("Deloitte");
        company.setCinNumber("XYAK12");
        company.setUsername("Del123");
        company.setPassword("123");
        company.setCompanyDescription("Cool Company");
        LinkedList<Company> companies= new LinkedList<>();
        companies.add(company);
        when(companyRepository.findAllCompaniesByNameStartingWith(isA(String.class), isA(Pageable.class))).thenReturn(companies);
        List<CompanyDTO> companyDTO= adminService.getCompaniesByName("Del",1,1);
        assertEquals(companyDTO.get(0).getName(),"Deloitte");
    }


    @Test
    public void getTotalNumberOfEmployees() {
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        employee.setStatus("Registered");
        LinkedList<Employee> employeeList=new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.count()).thenReturn(1L);
        Long totalEmployees= adminService.getTotalNumberOfEmployees();
        assertEquals(totalEmployees,1);
    }

    @Test
    public void getTotalNumberOfAcceptedEmployees() {
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        employee.setStatus("Accepted");
        LinkedList<Employee> employeeList=new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.findAllByStatus(isA(String.class))).thenReturn(employeeList);
        Integer totalEmployees= adminService.getTotalNumberOfAcceptedEmployees();
        assertEquals(totalEmployees,1);
    }

    public @Test
    void getTotalNumberOfPendingEmployees() {
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        employee.setStatus("Pending");
        LinkedList<Employee> employeeList=new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.findAllByStatus(isA(String.class))).thenReturn(employeeList);
        Integer totalEmployees= adminService.getTotalNumberOfPendingEmployees();
        assertEquals(totalEmployees,1);

    }

    @Test
    public void getTotalNumberOfRegisteredEmployees() {
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        employee.setStatus("Registered");
        LinkedList<Employee> employeeList=new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.findAllByStatus(isA(String.class))).thenReturn(employeeList);
        Integer totalEmployees= adminService.getTotalNumberOfRegisteredEmployees();
        assertEquals(totalEmployees,1);

    }

    @Test
    public void getTotalNumberOfRejectEmployees() {
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        employee.setStatus("Rejected");
        LinkedList<Employee> employeeList=new LinkedList<>();
        employeeList.add(employee);
        when(employeeRepository.findAllByStatus(isA(String.class))).thenReturn(employeeList);
        Integer totalEmployees= adminService.getTotalNumberOfRejectEmployees();
        assertEquals(totalEmployees,1);
    }

    @Test
    public void viewAllApplications() {
        List<Employee> employees = new ArrayList<>();
        Employee employeeToAdd = new Employee();
        employeeToAdd.setEmployeeId(1);
        employeeToAdd.setUsername("shekgupta");
        employeeToAdd.setEmployeeId(2);
        employeeToAdd.setUsername("sgupta");
        employees.add(employeeToAdd);
        employees.add(employeeToAdd);
        Page<Employee> pages = new PageImpl<>(employees, PageRequest.of(1,2) , employees.size());
        when(employeeRepository.findAll(isA(Pageable.class))).thenReturn(pages);
        List<EmployeeDTO> employeesDTO = adminService.viewAllApplications(2, 1,"test","all");
        assertEquals(2, employeesDTO.size());
    }

    @Test
    public void viewEmployeeApplication() {
        Employee employeeToAdd = new Employee();
        employeeToAdd.setEmployeeId(1);
        employeeToAdd.setCompanyId(1);
        employeeToAdd.setUsername("shekgupta");

        when(employeeRepository.findById(isA(Integer.class))).thenReturn(java.util.Optional.of(employeeToAdd));
        EmployeeDTO employeeDTO = adminService.viewEmployeeApplication(1);

        assertEquals(employeeToAdd.getEmployeeId(), employeeDTO.getEmployeeId() );
    }

    @Test
    public void getCompanies() {
        List<Company> companies = new ArrayList<>();
        Company companyToAdd = new Company();
        companyToAdd.setCompanyId(1);
        companies.add(companyToAdd);
        companyToAdd.setCompanyId(2);
        companies.add(companyToAdd);
        Page<Company> pages = new PageImpl<>(companies, PageRequest.of(1,2) , companies.size());
        when(companyRepository.findAll(isA(Pageable.class))).thenReturn(pages);
        List<CompanyDTO> companiesDTO = adminService.getCompanies(2, 1);
        assertEquals(2, companiesDTO.size());
    }

    @Test
    public void viewAllApplicantsByName() {
        List<Employee> employees = new ArrayList<>();
        Employee employeeToAdd = new Employee();
        employeeToAdd.setEmployeeId(1);
        employeeToAdd.setCompanyId(1);
        employeeToAdd.setUsername("shekgupta");
        employeeToAdd.setFirstName("Shekhar");
        employees.add(employeeToAdd);
        employeeToAdd.setEmployeeId(2);
        employeeToAdd.setUsername("sgupta");
        employees.add(employeeToAdd);
        Page<Employee> pages = new PageImpl<>(employees, PageRequest.of(1,2) , employees.size());
        when(employeeRepository.findAllByDisplayNameStartingWithAndStatus(isA(String.class), isA(String.class), isA(Pageable.class))).thenReturn(employees);
        List<EmployeeDTO> employeeDTOS = adminService.viewAllApplicantsByName("Shekhar", 1, 2, "test","al");
        assertEquals(2, employeeDTOS.size());
    }

}
