package com.hashedin.product.kyeazy.TestServices;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.DataAlreadyExistsException;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import com.hashedin.product.kyeazy.services.CompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTest {
    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepository companyRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    public void shouldAddCompany() {
        Company companyToregister =new Company();
        companyToregister.setName("Deloitte");
        companyToregister.setCinNumber("XYAK12");
        companyToregister.setUsername("Del123");
        companyToregister.setPassword("123");
        companyToregister.setCompanyDescription("Cool Company");

        Company companyAfterAdding = new Company();
        companyAfterAdding.setCompanyId(1);
        companyAfterAdding.setName("Deloitte");
        companyAfterAdding.setCinNumber("XYAK12");
        companyAfterAdding.setUsername("Del123");
        companyAfterAdding.setPassword("123");
        companyAfterAdding.setCompanyDescription("Cool Company");
        when(companyRepository.save(isA(Company.class))).thenReturn(companyAfterAdding);
        when(companyRepository.findAll()).thenReturn(new LinkedList<>());
        CompanyDTO companyDTO = companyService.register(companyToregister);
        assertNotNull(companyDTO.getCompanyId());
        assertEquals(companyDTO.getCompanyId(), 1);
        verify(companyRepository, times(1)).save(companyToregister);
    }

    @Test
    public void shouldGetCompanyDetails(){
        Company company=new Company();
        company.setCompanyId(1);
        company.setName("Deloitte");
        company.setCinNumber("XYAK12");
        company.setUsername("Del123");
        company.setPassword("123");
        company.setCompanyDescription("Cool Company");

        when(companyRepository.findById(isA(Integer.class))).thenReturn(java.util.Optional.of(company));
        CompanyDTO companyDTO = companyService.getCompanyDetails(1);
        assertEquals(companyDTO.getCompanyId(),1);
        assertEquals(companyDTO.getCompanyDescription(),"Cool Company");
    }

    @Test
    public void shouldGetEmployee(){
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        LinkedList<Employee> employeeList=new LinkedList<>();
        employeeList.add(employee);

        when(employeeRepository.findAllByCompanyId(isA(Integer.class),isA(Pageable.class))).thenReturn(employeeList);
        List<EmployeeDTO> employees=companyService.getEmployees(1,1,2,"test","all");
        assertEquals(employees.size(),1);
    }

    @Test
    public void shouldThrowExceptionWhenRequiredFieldDoNotExist() {
        Company company=new Company();
        company.setUsername("");
        company.setCinNumber("");
        company.setName("");

        assertThrows(NullPointerException.class, () -> {
            companyService.register(company);
        });
    }

    @Test
    public void shouldThrowAlreadyExistsExceptionWhenSentDuplicateData() {
        Company company= new Company();
        company.setCompanyId(1);
        company.setName("Deloitte");
        company.setCinNumber("XYAK12");
        company.setUsername("Del123");
        company.setPassword("123");
        company.setCompanyDescription("Cool Company");
        LinkedList<Company> companies=new LinkedList<>();
        companies.add(company);
        when(companyRepository.findAll()).thenReturn(companies);

        assertThrows(DataAlreadyExistsException.class, () -> {
            companyService.register(company);
        });

    }

//    @Test
//    public void shouldReportEmployees(){
//        Employee employee=new Employee();
//        employee.setDisplayName("Kartikey");
//        employee.setEmployeeId(1);
//        employee.setGender("Male");
//        employee.setUsername("Kar123");
//        employee.setPassword("Kar123#");
//        employee.setStatus("Registered");
//
//        when(employeeRepository.findById(isA(Integer.class))).thenReturn(java.util.Optional.of(employee));
//        ActionDTO actionDTO=companyService.reportEmployee(employee.getEmployeeId(),"Settlement Pending");
//       assertEquals(employee.getStatus(),"Reported");
//    }

    @Test
    public void shouldGetEmployees(){
        Employee Kartikey =new Employee();
        Kartikey.setDisplayName("Kartikey");
        Kartikey.setEmployeeId(1);
        Kartikey.setGender("Male");
        Kartikey.setUsername("Kar123");
        Kartikey.setPassword("Kar123#");
        Kartikey.setStatus("Registered");
        Kartikey.setDateTimeOfApplication(new Date());
        Employee Riya =new Employee();
        Riya.setDisplayName("Riya");
        Riya.setEmployeeId(2);
        Riya.setGender("Female");
        Riya.setUsername("Riya123");
        Riya.setPassword("Riya123#");
        Riya.setStatus("Registered");
        Riya.setDateTimeOfApplication(new Date());
        LinkedList<Employee> employeeList=new LinkedList<>();
        employeeList.add(Riya);
        employeeList.add(Kartikey);
        when(employeeRepository.findAllByCompanyId(isA(Integer.class),(isA(Pageable.class)))).thenReturn(employeeList);
        List<EmployeeDTO> employeeDTO=companyService.getEmployees(1,1,2,"test","all");
        assertEquals(employeeDTO.size(),2);
    }

}
