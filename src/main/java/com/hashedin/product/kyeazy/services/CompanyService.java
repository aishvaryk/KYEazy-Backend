package com.hashedin.product.kyeazy.services;
import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.RequiredFieldException;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
@Service
public class CompanyService {

    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;

    private EmailSender emailSender;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, EmailSender emailSender, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.emailSender=emailSender;
        this.employeeRepository=employeeRepository;
    }

    public ActionDTO register(Company company) {

        //validation for username and company name uniqueness
        if (company.getName() == null || company.getName().length() == 0) {
            throw new RequiredFieldException("Company name can't be empty.");
        }
        if (company.getCINNumber() == null || company.getCINNumber().length() == 0) {
            throw new RequiredFieldException("First name can't be empty.");
        }
        if (company.getCompanyDescription() == null || company.getCompanyDescription().length() == 0) {
            throw new RequiredFieldException("Company Description can't be empty.");
        }
        Company addedCompany = companyRepository.save(company);
        return new ActionDTO(addedCompany.getCompanyId(), true, "Company Added Successfully");
    }

    public ActionDTO registerEmployee(Employee employee) {

        String username = this.generateUsername(employee);
        String passkey = String.valueOf(this.generatePassword(employee));
        String link="http://localhost:8085/employee/login";
        String mailBody="Hey "+employee.getFirstName()+","+"Your Id password for doing kyc is  Username: "+username+" Password:" +passkey +" Link:" +link;
        this.emailSender.sendMail(employee.getEmailID(),"Regarding KYC",mailBody);
        employee.setUsername(username);
        employee.setUsername(passkey);
        employee.setStatus("Pending");
        Employee addedEmployee = employeeRepository.save(employee);

        return new ActionDTO(employee.getEmployeeId(), true, "Employee KYC Under Progress");
    }



    private String generateUsername(Employee employee) {
        String username="";
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        username+=employee.getFirstName().substring(0,1);
        username+=employee.getLastName();
        username+=uuidAsString.substring(0,3);
        return username;
    }

    private static char[] generatePassword(Employee employee)
    {
        String name=employee.getFirstName();

        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$_";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[7];
        password[0] = name.toUpperCase().charAt(0);
        password[1] = name.toLowerCase().charAt(1);
        password[2] = name.toLowerCase().charAt(2);
        password[3] = numbers.charAt(random.nextInt(numbers.length()));
        password[4] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));

        for(int i = 5; i< 7 ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;

    }

    }

