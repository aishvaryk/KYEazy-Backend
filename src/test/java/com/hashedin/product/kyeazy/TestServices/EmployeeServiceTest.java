package com.hashedin.product.kyeazy.TestServices;
import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import com.hashedin.product.kyeazy.services.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
    @InjectMocks
    EmployeeService employeeService;

    @Mock
    CompanyRepository companyRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Test
    public void shouldUpdateEmployeeStatus(){
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        employee.setStatus("Registered");

        Employee employeeAfterUpdating =new Employee();
        employeeAfterUpdating.setDisplayName("Kartikey");
        employeeAfterUpdating.setEmployeeId(1);
        employeeAfterUpdating.setGender("Male");
        employeeAfterUpdating.setUsername("Kar123");
        employeeAfterUpdating.setPassword("Kar123#");
        employeeAfterUpdating.setStatus("Pending");

        when(employeeRepository.findById(isA(Integer.class))).thenReturn(java.util.Optional.of(employee));
        when(employeeRepository.save(isA(Employee.class))).thenReturn(employeeAfterUpdating);
        ActionDTO ActionDTO=employeeService.updateEmployeeStatus(1);
        assertEquals(ActionDTO.getId(),1);
        assertEquals(employee.getStatus(),"Pending");
        assertNotNull(ActionDTO);
    }

    @Test
    public void shouldGetEmployeeData(){
        Employee employee=new Employee();
        employee.setDisplayName("Kartikey");
        employee.setEmployeeId(1);
        employee.setGender("Male");
        employee.setUsername("Kar123");
        employee.setPassword("Kar123#");
        employee.setStatus("Registered");

        when(employeeRepository.findById(isA(Integer.class))).thenReturn(java.util.Optional.of(employee));
        Employee employeeResult = employeeService.getEmployeeData(1);
        assertNotNull(employeeResult);
        assertEquals(employeeResult.getEmployeeId(),1);

    }

    @Test
    public void shouldGetEmployeeByUsername(){
        Employee Kartikey =new Employee();
        Kartikey.setDisplayName("Kartikey");
        Kartikey.setEmployeeId(1);
        Kartikey.setGender("Male");
        Kartikey.setUsername("Kar123");
        Kartikey.setPassword("Kar123#");
        Employee Riya =new Employee();
        Riya.setDisplayName("Riya");
        Riya.setEmployeeId(2);
        Riya.setGender("Female");
        Riya.setUsername("Riya123");
        Riya.setPassword("Riya123#");
        Riya.setStatus("Registered");
        LinkedList<Employee> employeeList=new LinkedList<>();
        employeeList.add(Riya);
        employeeList.add(Kartikey);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        Employee employee = employeeService.getEmployeeByUsername("Riya123");
        assertEquals(employee.getEmployeeId(),2);

    }
}
