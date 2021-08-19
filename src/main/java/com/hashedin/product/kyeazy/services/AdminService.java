package com.hashedin.product.kyeazy.services;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;

    public EmployeeDTO viewEmployeeApplication(Integer employeeId)
    {
        EmployeeDTO employeeDTO;
        Employee employee=getEmployeeById(employeeId);
        return parseEmployee(employee);
    }

    public List<Employee> viewAllApplications()
    {
        List<Employee> employee=employeeRepository.findAll();
        return employee;
    }
    public Set<EmployeeDTO> viewPendingApplications()
    { Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        List<Employee> employee=employeeRepository.findAll();
        Set<Employee> pendingApplications = employee.stream().filter(p->{ return p.getStatus().equalsIgnoreCase("Pending");}).collect(Collectors.toSet());
        for(Employee e:pendingApplications)
        {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }
    public Set<EmployeeDTO> viewAcceptedApplications()
    {
        Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        List<Employee> employee=employeeRepository.findAll();
        Set<Employee> acceptedApplications = employee.stream().filter(p->{ return p.getStatus().equalsIgnoreCase("Accepted");}).collect(Collectors.toSet());
        for(Employee e:acceptedApplications)
        {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }
    public Set<EmployeeDTO> viewRejectedApplications()
    {   Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        List<Employee> employee=employeeRepository.findAll();
        Set<Employee> rejectedApplications = employee.stream().filter(p->{ return p.getStatus().equalsIgnoreCase("Rejected");}).collect(Collectors.toSet());
        for(Employee e:rejectedApplications)
        {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }
    public EmployeeDTO verify(String status,Integer id){
        Employee employee=getEmployeeById(id);
        employee.setStatus(status);
        employee.setDateTimeOfVerification(new Date());
        employeeRepository.save(employee);
        return parseEmployee(employee);
    }

    private Employee getEmployeeById(Integer employeeId)
    {
        return employeeRepository.findById(employeeId).get();
    }
    public byte[] getEmployeeImage(Integer employeeId) {
        Employee employee=employeeRepository.findById(employeeId).get();
        byte[] profilePictureBytes = employee.getCapturedImage();
        return profilePictureBytes;
    }

    private EmployeeDTO parseEmployee(Employee employee)
    {
        EmployeeDTO employeeDTO=new EmployeeDTO();
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

        //  employeeDTO.setCapturedImage(employee.getCapturedImage());
        return  employeeDTO;
    }

}
