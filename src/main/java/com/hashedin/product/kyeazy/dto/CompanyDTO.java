package com.hashedin.product.kyeazy.dto;

import com.hashedin.product.kyeazy.entities.Address;
import com.hashedin.product.kyeazy.entities.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Integer companyId;
    private  String username;
    private  String name;
    private  String companyDescription;
    private  String  cinNumber;
    private  String address;
    private  byte[] icon;
    private String emailId;
    private List<EmployeeDTO> employees;
    private Integer numberOfPendingEmployees;
    private Integer numberOfRegisteredEmployees;
    private Integer numberOfReportedEmployees;
    private Integer numberOfAcceptedEmployees;
    private Integer numberOfRejectedEmployees;
    private Integer numberOfTotalEmployees;
    private Integer coins;
    private Integer plan;


}
