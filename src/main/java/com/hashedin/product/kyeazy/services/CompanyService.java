package com.hashedin.product.kyeazy.services;
import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.DataNotFoundException;
import com.hashedin.product.kyeazy.exceptions.RequiredFieldException;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import jdk.dynalink.linker.GuardingDynamicLinkerExporter;
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

        List<Company> companies=companyRepository.findAll();
        for(Company companyToCheck:companies)
        {
            if(companyToCheck.getUsername().equals(company.getUsername()))throw new DataNotFoundException("The Username is already registered !!!!!");
            if(companyToCheck.getCinNumber().equalsIgnoreCase(company.getCinNumber()))throw new DataNotFoundException("The Company is already registered !!!!!");
        }
//        if (company.getName() == null || company.getName().length() == 0) {
//            throw new RequiredFieldException("Company name can't be empty.");
//        }
//
//        if (company.getCompanyDescription() == null || company.getCompanyDescription().length() == 0) {
//            throw new RequiredFieldException("Company Description can't be empty.");
//        }
        Company addedCompany = companyRepository.save(company);
        return new ActionDTO(addedCompany.getCompanyId(), true, "Company Added Successfully");
    }

    public ActionDTO registerEmployee(Employee employee,int companyId) {

        List<Employee> employees=employeeRepository.findAll();
        for(Employee employeeToCheck:employees)
        {
            if(employeeToCheck.getEmailID().equals(employee.getEmailID()) && employeeToCheck.getCompanyId()==companyId)throw new DataNotFoundException("The given is already registered with same company !!!!!");
        }

        String username = this.generateUsername(employee);
        String passkey = String.valueOf(this.generatePassword(employee));

        String link="http://localhost:8085/employee/login";
        String mailBody="Hey "+employee.getFirstName()+","+"Your Id password for doing kyc is  Username: "+username+" Password:" +passkey +" Link:" +link;
        //this.emailSender.sendMail(employee.getEmailID(),"Regarding KYC",mailBody);

        employee.setUsername(username);
        employee.setPassword(passkey);

        employee.setStatus("Registered");
        employee.setDateTimeOfApplication(new Date());
        employee.setCompanyId(companyId);
        Employee addedEmployee = employeeRepository.save(employee);
        return new ActionDTO(addedEmployee.getEmployeeId(), true, "Employee KYC Under Progress Wait for 2-3 days");
    }
    @Transactional
    public List<EmployeeDTO> getEmployees(Integer id,Integer pageNumber,Integer pageSize)
    {
        Company company= getCompanyById(id);

        List<EmployeeDTO> employeeDTOS=new LinkedList<>();

        for(Employee employee:this.getEmployeePagination(pageNumber,pageSize,company.getEmployees()))
        {
            employeeDTOS.add(parseEmployee(employee));
        }

        return  employeeDTOS;
    }


    @Transactional
    public Set<EmployeeDTO> getEmployeesByStatus(Integer companyId, String status,Integer pageNumber,Integer pageSize){
        Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        Company company= getCompanyById(companyId);
        Set<Employee> employeesByStatus=company.getEmployees().stream().filter(p->{ return p.getStatus().equalsIgnoreCase(status);}).collect(Collectors.toSet());
        if(employeesByStatus.isEmpty())
            return employeeDTOS;
        for(Employee e:this.getEmployeePagination(pageNumber,pageSize,employeesByStatus))
        {
            employeeDTOS.add(parseEmployee(e));
        }

        return  employeeDTOS;
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


    public CompanyDTO getCompanyDetails(Integer id)
    {
        Company company=getCompanyById(id);
        return parseCompany(company);
    }

    public EmployeeDTO getEmployeeByName(Integer companyId,String name) {
        Company company = getCompanyById(companyId);
        Set<Employee> employeeList = company.getEmployees();
        Employee employeebyname= employeeList.stream()
                .filter(employee -> name.startsWith(employee.getFirstName() + employee.getLastName()))
                .findAny()
                .orElse(null);
        return parseEmployee(employeebyname);
    }
    @Transactional
    public Set<EmployeeDTO> getEmployeesSortedByName(Integer id,Integer pageNumber,Integer pageSize)
    {   Company company=companyRepository.findById(id).get();
        Set<EmployeeDTO> employeeDTOS=new LinkedHashSet<>();
        Set<Employee> employeeSorted= company.getEmployees();
//    {
//        Set<EmployeeDTO> employeeDTOS=new LinkedHashSet<>();
//        List<Employee> employee=employeeRepository.findAll();
//        LinkedHashSet<Employee> employeeSorted= employee.stream().filter(p->{ return p.getCompanyId()==id;}).collect(Collectors.toCollection(LinkedHashSet::new));
        for(Employee e:getSortedEmployeePagination(pageNumber,pageSize,employeeSorted,"name"))
        {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }

    @Transactional
    public Set<EmployeeDTO> getEmployeesSortedByDate(Integer id,Integer pageNumber,Integer pageSize)
    {
        Company company=companyRepository.findById(id).get();
        Set<EmployeeDTO> employeeDTOS=new LinkedHashSet<>();
        Set<Employee> employeeSorted= company.getEmployees();;
//        List<Employee> employee=employeeRepository.findAll();
//        LinkedHashSet<Employee> employeeSorted= employee.stream().filter(p->{ return p.getCompanyId()==id;}).collect(Collectors.toCollection(LinkedHashSet::new));
        for(Employee e:getSortedEmployeePagination(pageNumber,pageSize,employeeSorted,"date"))
        {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }

    @Transactional
    public Set<EmployeeDTO> getRegisteredEmployees(Integer id,Integer pageNumber,Integer pageSize)
    {   Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        Company company=getCompanyById(id);
        Set<Employee> employee=company.getEmployees();
        Set<Employee> registeredEmployees =company.getEmployees().stream().filter(p->{ return p.getStatus().equalsIgnoreCase("Registered");}).collect(Collectors.toSet());
        for(Employee e:this.getEmployeePagination(pageNumber,pageSize,registeredEmployees))
        {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }
    @Transactional
    public Set<EmployeeDTO> getEmployeesByDateOfApplication(String date,Integer pageNumber,Integer pageSize)
    {
        Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        List<Employee> employeeList=employeeRepository.findAll();
       Set<Employee> employees=new HashSet<>();
        for(Employee e:employeeList){
            String s=e.getDateTimeOfApplication().toString();
            if(s.equalsIgnoreCase(date))
            {
                employees.add(e);
            }

        }
        for(Employee employee:this.getEmployeePagination(pageNumber,pageSize,employees))
        {
            employeeDTOS.add(parseEmployee(employee));
        }
        return employeeDTOS;
    }

    private List<Employee> getEmployeePagination(Integer pageNumber,Integer pageSize,Set<Employee> employees)
    {

        Integer lastIndex=employees.size();
        Integer from=(pageNumber-1)*pageSize;
        Integer to=from+pageSize;
        if(from>employees.size()-1) return null;
        if(to>lastIndex) to=lastIndex;
        List<Employee> employeeList=new ArrayList<>();

        employees.stream().sorted(Comparator.comparing(Employee::getEmployeeId)).forEach((p)->{
            employeeList.add(p);
                });

        return employeeList.subList(from,to);

    }

    private List<Employee> getSortedEmployeePagination(Integer pageNumber,Integer pageSize,Set<Employee> employees,
                                                       String parameter )
    {

        Integer lastIndex=employees.size();
        Integer from=(pageNumber-1)*pageSize;
        Integer to=from+pageSize;
        if(from>employees.size()-1) return null;
        if(to>lastIndex) to=lastIndex;
        List<Employee> employeeList=new ArrayList<>();
        if(parameter.equalsIgnoreCase("name")) {
            employees.stream().sorted(Comparator.comparing(Employee::getFirstName)).forEach((p) -> {
                employeeList.add(p);
            });
        }
        if(parameter.equalsIgnoreCase("date")) {
            employees.stream().sorted(Comparator.comparing(Employee::getDateTimeOfApplication)).forEach((p) -> {
                employeeList.add(p);
            });
        }

        return employeeList.subList(from,to);

    }
    @Transactional
    private Company getCompanyById(Integer companyId)
    {
        return companyRepository.findById(companyId).get();
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
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setCapturedImage(employee.getCapturedImage());
        employeeDTO.setDocumentType(employee.getDocumentType());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setGender(employee.getGender());
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

    private CompanyDTO parseCompany(Company company)
    {
        CompanyDTO companyDTO=new CompanyDTO();
        List<EmployeeDTO> employeeDTOS=new LinkedList<>();
        EmployeeDTO employeeDTO ;
        Integer pendingEmployees=0;
        Integer rejectedEmployees=0;
        Integer acceptedEmployees=0;
        Integer totalEmployees=0;
        for(Employee employee:company.getEmployees())
        {
            if(employee.getStatus().equalsIgnoreCase("Pending"))
            {
                pendingEmployees+=1;
            }
            if(employee.getStatus().equalsIgnoreCase("Rejected"))
            {
                rejectedEmployees+=1;
            }
            if(employee.getStatus().equalsIgnoreCase("Accepted"))
            {
                acceptedEmployees+=1;
            }
            totalEmployees+=1;
            employeeDTO=parseEmployee(employee);
            employeeDTOS.add(employeeDTO);

        }
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
        return  companyDTO;
    }


}




