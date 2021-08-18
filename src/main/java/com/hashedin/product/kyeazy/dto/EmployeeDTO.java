package com.hashedin.product.kyeazy.dto;

import com.hashedin.product.kyeazy.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

    private Integer employeeId;
    private String username;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String documentType;
    private String documentNumber;
    private String emailID;
    private byte[] capturedImage;
    private String status;
    private Date dateTimeOfVerification;
    private Date dateTimeOfApplication;
    private Address address;
    private  Integer companyId;


    }
