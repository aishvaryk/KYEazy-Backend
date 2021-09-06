package com.hashedin.product.kyeazy.utils;

import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;

import java.util.LinkedList;
import java.util.List;

public class Parser {

    public static CompanyDTO parseCompany(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        List<EmployeeDTO> employeeDTOS = new LinkedList<>();
        EmployeeDTO employeeDTO;
        int pendingEmployees = 0;
        int rejectedEmployees = 0;
        int acceptedEmployees = 0;
        int registeredEmployee = 0;
        int reportedEmployee = 0;
        int totalEmployees = 0;
        for (Employee employee : company.getEmployees()) {
            if (employee.getStatus().equalsIgnoreCase("Pending")) {
                pendingEmployees += 1;
            }
            if (employee.getStatus().equalsIgnoreCase("Rejected")) {
                rejectedEmployees += 1;
            }
            if (employee.getStatus().equalsIgnoreCase("Accepted")) {
                acceptedEmployees += 1;
            }
            if (employee.getStatus().equalsIgnoreCase("Reported")) {
                reportedEmployee += 1;
            }
            if (employee.getStatus().equalsIgnoreCase("Registered")) {
                registeredEmployee += 1;
            }
            totalEmployees += 1;
            employeeDTO = parseEmployee(employee);
            employeeDTOS.add(employeeDTO);
        }
        companyDTO.setNumberOfRegisteredEmployees(registeredEmployee);
        companyDTO.setNumberOfReportedEmployees(reportedEmployee);
        companyDTO.setNumberOfTotalEmployees(totalEmployees);
        companyDTO.setNumberOfPendingEmployees(pendingEmployees);
        companyDTO.setNumberOfRejectedEmployees(rejectedEmployees);
        companyDTO.setNumberOfAcceptedEmployees(acceptedEmployees);
        companyDTO.setEmployees(employeeDTOS);
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setCompanyDescription(company.getCompanyDescription());
        companyDTO.setName(company.getName());
        companyDTO.setCinNumber(company.getCinNumber());
        companyDTO.setUsername(company.getUsername());
        companyDTO.setAddress(companyDTO.getAddress());
        companyDTO.setIcon(company.getIcon());
        companyDTO.setCoins(company.getCoins());
        companyDTO.setPlanCoins(company.getPlan());
        return companyDTO;
    }

    public static EmployeeDTO parseEmployee(Employee employee) {
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
        employeeDTO.setQuestion(employee.getQuestion());
        return employeeDTO;
    }

}
