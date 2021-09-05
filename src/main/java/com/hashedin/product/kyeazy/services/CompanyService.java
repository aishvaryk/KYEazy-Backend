package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.DataAlreadyExistsException;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
public class CompanyService {

    private EmployeeRepository employeeRepository;
    private CompanyRepository companyRepository;
    private EmailSender emailSender;

    @Autowired
    public CompanyService(CompanyRepository companyRepository, EmailSender emailSender, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.emailSender = emailSender;
        this.employeeRepository = employeeRepository;
    }

    public Company getCompanyByUsername(String userName) {
        List<Company> companies = companyRepository.findAll();
        for (Company companyToCheck : companies) {
            if (companyToCheck.getUsername().equals(userName)) return companyToCheck;

        }
        return null;
    }

    public CompanyDTO register(Company company) {
        List<Company> companies = companyRepository.findAll();
        for (Company companyToCheck : companies) {
            if (companyToCheck.getUsername().equals(company.getUsername()))
                throw new DataAlreadyExistsException("The Username is already registered !!!!!");
            if (companyToCheck.getCinNumber().equalsIgnoreCase(company.getCinNumber()))
                throw new DataAlreadyExistsException("The Company is already registered !!!!!");
        }
        Company addedCompany = companyRepository.save(company);
        return parseCompany(addedCompany);
    }

    public ActionDTO registerEmployee(Employee employee, int companyId) throws DataAlreadyExistsException {
        List<Employee> employees = employeeRepository.findAll();
        for (Employee employeeToCheck : employees) {
            if (employeeToCheck.getEmailID().equals(employee.getEmailID()) && employeeToCheck.getCompanyId() == companyId)
                throw new DataAlreadyExistsException("The given is already registered with same company !!!!!");
            if (employeeToCheck.getEmailID().equals(employee.getEmailID()))
            {
                employeeToCheck.setCompanyId(companyId);
                return null ;
            }
        }
        String username = this.generateUsername(employee);
        String passkey = String.valueOf(this.generatePassword(employee));
        String link = "https://kycfront-amxbp6pvia-as.a.run.app/";
        String mailBody = "Hey " + employee.getFirstName() + "," + "Your Id password for doing kyc is  Username: " + username + " Password:" + passkey + " Link:" + link;
        this.emailSender.sendMail(employee.getEmailID(), "Regarding KYC", mailBody);
        employee.setUsername(username);
        employee.setPassword(passkey);
        employee.setStatus("Registered");
        employee.setDateTimeOfApplication(new Date());
        employee.setCompanyId(companyId);
        Employee addedEmployee = employeeRepository.save(employee);
        return new ActionDTO(addedEmployee.getEmployeeId(), true, "Employee KYC Under Progress Wait for 2-3 days");
    }

    @Transactional
    public List<EmployeeDTO> getEmployees(Integer id, Integer pageNumber, Integer pageSize) {
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<EmployeeDTO> employeeDTOS = new LinkedList<>();
        for (Employee employee : employeeRepository.findAllByCompanyId(id, pageable)) {
            employeeDTOS.add(parseEmployee(employee));
        }
        return employeeDTOS;
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
    public CompanyDTO getCompanyDetails(Integer id) {
        Company company = getCompanyById(id);
        return parseCompany(company);
    }

    @Transactional
    public List<EmployeeDTO> getEmployeeByName(Integer companyId, String name, Integer pageNumber, Integer pageSize) {
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<EmployeeDTO> employeeDTOS = new LinkedList<>();
        for (Employee employee : employeeRepository.findAllByDisplayNameStartingWithAndCompanyId(name, companyId, pageable)) {
            employeeDTOS.add(parseEmployee(employee));
        }
        return employeeDTOS;
    }

    @Transactional
    public List<EmployeeDTO> getEmployeesSortedByName(Integer id, Integer pageNumber, Integer pageSize) {
        List<EmployeeDTO> allEmployeeDTOS = new LinkedList<>();
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("displayName"));

        for (Employee e : employeeRepository.findAllByCompanyId(id, pageable)) {
            allEmployeeDTOS.add(parseEmployee(e));
        }
        return allEmployeeDTOS;
    }

    @Transactional
    public List<EmployeeDTO> getEmployeesSortedByDate(Integer id, Integer pageNumber, Integer pageSize) {
        pageNumber--;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dateTimeOfApplication").descending());

        List<EmployeeDTO> allEmployeeDTOS = new LinkedList<>();
        for (Employee e : employeeRepository.findAllByCompanyId(id, pageable)) {
            allEmployeeDTOS.add(parseEmployee(e));
        }
        return allEmployeeDTOS;
    }

    public ActionDTO updateCompanyImage(Integer companyId, MultipartFile icon) throws IOException
    {
        Company company=companyRepository.findById(companyId).get();
        company.setIcon(icon.getBytes());
        Company savedCompany = companyRepository.save(company);
        return new ActionDTO(savedCompany.getCompanyId(),true,"Company Details Added Successfully.");
    }

    public List<EmployeeDTO> reportEmployee(Integer employeeId, String message){
        Employee employee=employeeRepository.findById(employeeId).get();
        employee.setStatus("Reported");
        employee.setReview(message);
        employeeRepository.save(employee);
        return getEmployees(employee.getCompanyId(), 1, 5);
    }

    @Transactional
    private Company getCompanyById(Integer companyId) {
        return companyRepository.findById(companyId).get();
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
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setCapturedImage(employee.getCapturedImage());
        employeeDTO.setDocumentType(employee.getDocumentType());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setGender(employee.getGender());
        return employeeDTO;
    }

    private String generateUsername(Employee employee) {
        String username = "";
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        username += employee.getFirstName().substring(0, 1);
        username += employee.getLastName();
        username += uuidAsString.substring(0, 3);
        return username;
    }

    private static char[] generatePassword(Employee employee) {
        String name = employee.getFirstName();
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
        for (int i = 5; i < 7; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password;
    }

 @Transactional
    private CompanyDTO parseCompany(Company company) {

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
        companyDTO.setNumberOfReportedEmployees(reportedEmployee)           ;
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


    public ActionDTO registerEmployees(Integer id, MultipartFile employeesList) throws IOException {
        String DELIMITER = ",";
        InputStreamReader isr = new InputStreamReader(employeesList.getInputStream(),
                StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String line;
        //String[] columns=[];
        Employee employee;
        br.readLine();
        while ((line = br.readLine()) != null) {
            employee = new Employee();
            String[] columns = line.split(DELIMITER);
            employee.setFirstName(columns[0]);
            employee.setLastName(columns[1]);
            employee.setEmailID(columns[2]);
            employee.setContactNumber(columns[3]);
            employee.setCompanyId(id);
            employee.setStatus("Registered");
            employeeRepository.save(employee);
        }
        return new ActionDTO(1, true, "Employees Added Successfully !");
    }


}




