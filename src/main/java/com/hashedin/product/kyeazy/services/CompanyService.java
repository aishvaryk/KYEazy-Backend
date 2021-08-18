package com.hashedin.product.kyeazy.services;
import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.RequiredFieldException;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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

        //company uniqueness check
        if (company.getName() == null || company.getName().length() == 0) {
            throw new RequiredFieldException("Company name can't be empty.");
        }

        if (company.getCompanyDescription() == null || company.getCompanyDescription().length() == 0) {
            throw new RequiredFieldException("Company Description can't be empty.");
        }
        Company addedCompany = companyRepository.save(company);
        return new ActionDTO(addedCompany.getCompanyId(), true, "Company Added Successfully");
    }

    public ActionDTO registerEmployee(Employee employee,int companyId) {

        //find By Email Verification
        if (employee.getContactNumber() == null || employee.getContactNumber().length() == 0) {
            throw new RequiredFieldException("Please enter contact Number.");
        }
        if (employee.getEmailID() == null || employee.getEmailID().length() == 0) {
            throw new RequiredFieldException("Please Enter email Id.");
        }
        if (employee.getFirstName() == null || employee.getFirstName().length() == 0) {
            throw new RequiredFieldException("Please Enter employee first name");
        }

        if (employee.getLastName() == null || employee.getLastName().length() == 0) {
            throw new RequiredFieldException("Please Enter employee Last name");
        }
        String username = this.generateUsername(employee);
        String passkey = String.valueOf(this.generatePassword(employee));

        String link="http://localhost:8085/employee/login";
        String mailBody="Hey "+employee.getFirstName()+","+"Your Id password for doing kyc is  Username: "+username+" Password:" +passkey +" Link:" +link;
        //this.emailSender.sendMail(employee.getEmailID(),"Regarding KYC",mailBody);

        employee.setUsername(username);
        employee.setPassword(passkey);

        employee.setStatus("Pending");
        employee.setDateTimeOfApplication(new Date());
        employee.setCompanyId(companyId);
        Employee addedEmployee = employeeRepository.save(employee);
        return new ActionDTO(addedEmployee.getEmployeeId(), true, "Employee KYC Under Progress Wait for 2-3 days");
    }
@Transactional
    public Set<EmployeeDTO> getEmployees(Integer id)
    {
        Company company= companyRepository.getById(id);

        Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        for(Employee employee:company.getEmployees())
        {
            employeeDTOS.add(parseEmployee(employee));
        }

        return  employeeDTOS;
    }


    @Transactional
    public Set<Employee> getEmployeesByStatus(Integer companyId,String status){
        Company company= companyRepository.getById(companyId);
        return company.getEmployees().stream().filter(p->{ return p.getStatus().equalsIgnoreCase(status);}).collect(Collectors.toSet());
    }

    public ActionDTO updateCompanyProfile(Company companyDetails)
    {
        Integer companyId=companyDetails.getCompanyId();
        Optional<Company> companyIdToCheck =companyRepository.findById(companyId);

        if(companyIdToCheck.isEmpty())  throw new RequiredFieldException("The given Company is not registered !!!!!");
        Company company=companyIdToCheck.get();

        if (companyDetails.getName() == null || companyDetails.getName().length() == 0) {
            throw new RequiredFieldException("Company name can't be empty.");
        }

        Company companyUpdated= companyRepository.save(company);
        return new ActionDTO(companyUpdated.getCompanyId(), true, "Company details Updated");
    }
    public ActionDTO getCompanyDetails(Integer id)
    {
        Company company=companyRepository.findById(id).get();
        return new ActionDTO();
    }
    @Transactional
    public List<Employee> getEmployeeByName(Integer companyId,String name)
    {
        Company company=companyRepository.findById(companyId).get();
        Set<Employee> employeeList=company.getEmployees();
        List<Employee> employees=new LinkedList<>();
        for(Employee e:employeeList){
            if(e.getFirstName().equals(name)) employees.add(e);
        }
        return  employees;
    }
    @Transactional
    public List<Employee> getEmployeesSortedByName()
    {
        List<Employee> employee=employeeRepository.findAll();
        //employee.sort(new ComparatorService());
        return  employee;
    }
    @Transactional
    public List<Employee> getEmployeesWithPendingKYC(Integer id)
    {
        Company company=companyRepository.findById(id).get();
        Set<Employee> employee=company.getEmployees();
        List<Employee> pendingEmployees = new LinkedList<>();
        for(Employee e:employee){
            if(e.getStatus().equals("pending"))
                pendingEmployees.add(e);
        }
        return pendingEmployees;
    }
    @Transactional
    public List<Employee> getEmployeesWithRejectedKYC(Integer id)
    {
        Company company=companyRepository.findById(id).get();
        Set<Employee> employee=company.getEmployees();
        List<Employee> rejectedEmployees = new LinkedList<>();
        for(Employee e:employee){
            if(e.getStatus().equals("rejected"))
                rejectedEmployees.add(e);
        }
        return rejectedEmployees;
    }
    @Transactional
    public List<Employee> getEmployeesByDateOfApplication(Date date)
    {
        List<Employee> employeeList=employeeRepository.findAll();
        List<Employee> employees=new LinkedList<>();
        for(Employee e:employeeList){
            if(e.getDateTimeOfVerification().equals(date))
                employees.add(e);
        }
        return  employees;
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

        //  employeeDTO.setCapturedImage(employee.getCapturedImage());
        return  employeeDTO;
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




