package com.hashedin.product.kyeazy.services;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;

    public EmployeeDTO viewEmployeeApplication(Integer employeeId) {

        EmployeeDTO employeeDTO;
        Employee employee = getEmployeeById(employeeId);
        return parseEmployee(employee);
    }

    @Transactional
    public List<EmployeeDTO> viewAllApplications(Integer pageNumber, Integer pageSize) {
        List<Employee> employees = employeeRepository.findAll();

        List<EmployeeDTO> employeeDTOS = new LinkedList<>();
        Set<Employee> employeeList = new HashSet<>();
        for (Employee employee : employees) {
            employeeList.add(employee);
        }
        for (Employee employee : this.getEmployeePagination(pageNumber, pageSize, employeeList)) {
            EmployeeDTO employeeDTO = parseEmployee(employee);
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    @Transactional
    public Set<EmployeeDTO> viewPendingApplications(Integer pageNumber, Integer pageSize) {
        Set<EmployeeDTO> employeeDTOS = new HashSet<>();
        List<Employee> employee = employeeRepository.findAll();
        Set<Employee> pendingApplications = employee.stream().filter(p -> {
            return p.getStatus().equalsIgnoreCase("Pending");
        }).collect(Collectors.toSet());
        for (Employee e : this.getEmployeePagination(pageNumber, pageSize, pendingApplications)) {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }

    @Transactional
    public List<EmployeeDTO> getEmployees(Integer id, Integer pageNumber, Integer pageSize) {
        Company company = getCompanyById(id);

        List<EmployeeDTO> employeeDTOS = new LinkedList<>();

        for (Employee employee : this.getEmployeePagination(pageNumber, pageSize, company.getEmployees())) {
            employeeDTOS.add(parseEmployee(employee));
        }

        return employeeDTOS;
    }

    @Transactional
    public List<CompanyDTO> getCompanies(Integer pageNumber, Integer pageSize) {
        List<Company> companies = companyRepository.findAll();
        Set<Company> companySet = companies.stream().collect(Collectors.toSet());

        List<CompanyDTO> companyDTOS = new LinkedList<>();

        for (Company company : this.getCompanyPagination(pageNumber, pageSize, companySet)) {
            companyDTOS.add(parseCompany(company));
        }

        return companyDTOS;
    }

    @Transactional
    public Set<EmployeeDTO> viewAcceptedApplications(Integer pageNumber, Integer pageSize) {
        Set<EmployeeDTO> employeeDTOS = new HashSet<>();
        List<Employee> employee = employeeRepository.findAll();

        Set<Employee> acceptedApplications = employee.stream().filter(p -> {
            return p.getStatus().equalsIgnoreCase("Accepted");
        }).collect(Collectors.toSet());
        for (Employee e : this.getEmployeePagination(pageNumber, pageSize, acceptedApplications)) {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }

    @Transactional
    public Set<EmployeeDTO> viewRejectedApplications(Integer pageNumber, Integer pageSize) {
        Set<EmployeeDTO> employeeDTOS = new HashSet<>();
        List<Employee> employee = employeeRepository.findAll();

        Set<Employee> rejectedApplications = employee.stream().filter(p -> {
            return p.getStatus().equalsIgnoreCase("Rejected");
        }).collect(Collectors.toSet());
        for (Employee e : this.getEmployeePagination(pageNumber, pageSize, rejectedApplications)) {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }

    public EmployeeDTO verify(String status, Integer id) {
        Employee employee = getEmployeeById(id);
        employee.setStatus(status);
        employee.setDateTimeOfVerification(new Date());
        employeeRepository.save(employee);
        return parseEmployee(employee);
    }

    private Employee getEmployeeById(Integer employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public byte[] getEmployeeImage(Integer employeeId) {
        Employee employee = getEmployeeById(employeeId);
        byte[] profilePictureBytes = employee.getCapturedImage();
        return profilePictureBytes;
    }

    private List<Company> getCompanyPagination(Integer pageNumber, Integer pageSize, Set<Company> companies) {

        Integer lastIndex = companies.size();
        Integer from = (pageNumber - 1) * pageSize;
        Integer to = from + pageSize;
        if (from > companies.size() - 1) return null;
        if (to > lastIndex) to = lastIndex;
        List<Company> companyList = new ArrayList<>();

        companies.stream().sorted(Comparator.comparing(Company::getCompanyId)).forEach((p) -> {
            companyList.add(p);
        });

        return companyList.subList(from, to);

    }

    @Transactional
    public List<CompanyDTO> getCompaniesByName(String name, Integer pageNumber, Integer pageSize) {
        System.out.println(name);
        List<Company> allCompanyList = companyRepository.findAll();
//        Set<Company> companySet= companies.stream().collect(Collectors.toSet());

        Set<Company> companiesByName = allCompanyList.stream()
                .filter(company -> company.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toSet());

        List<CompanyDTO> companyDTOS = new LinkedList<>();

        for (Company company : this.getCompanyPagination(pageNumber, pageSize, companiesByName)) {
            companyDTOS.add(parseCompany(company));
        }

        return companyDTOS;
    }

    @Transactional
    private CompanyDTO parseCompany(Company company) {
        CompanyDTO companyDTO = new CompanyDTO();
        List<EmployeeDTO> employeeDTOS = new LinkedList<>();
        EmployeeDTO employeeDTO;
        Integer pendingEmployees = 0;
        Integer rejectedEmployees = 0;
        Integer acceptedEmployees = 0;
        Integer totalEmployees = 0;
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
            totalEmployees += 1;
            employeeDTO = parseEmployee(employee);
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
        return companyDTO;
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
        employeeDTO.setDocumentType(employee.getDocumentType());
        employeeDTO.setStatus(employee.getStatus());
        employeeDTO.setCapturedImage(employee.getCapturedImage());
        employeeDTO.setGender(employee.getGender());
        //  employeeDTO.setCapturedImage(employee.getCapturedImage());
        return employeeDTO;
    }

    @Transactional
    private Company getCompanyById(Integer companyId) {
        return companyRepository.findById(companyId).get();
    }

    private List<Employee> getEmployeePagination(Integer pageNumber, Integer pageSize, Set<Employee> employees) {

        Integer lastIndex = employees.size();
        Integer from = (pageNumber - 1) * pageSize;
        Integer to = from + pageSize;
        if (from > employees.size() - 1) return null;
        if (to > lastIndex) to = lastIndex;
        List<Employee> employeeList = new ArrayList<>();

        employees.stream().sorted(Comparator.comparing(Employee::getEmployeeId)).forEach((p) -> {
            employeeList.add(p);
        });

        return employeeList.subList(from, to);

    }
    @Transactional
    public List<EmployeeDTO> viewAllApplicantsByName(String name, Integer pageNumber,Integer pageSize) {
        System.out.println(name);
        List<Employee> allEmployeeList = employeeRepository.findAll();
        List<Employee> employeebyname= allEmployeeList.stream()
                .filter(employee -> (employee.getFirstName() + " " + employee.getLastName()).toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        List<EmployeeDTO> employeeDTOS=new LinkedList<>();
        Set<Employee> employeeList=new HashSet<>();
        if (employeebyname.isEmpty()) {
            return employeeDTOS;
        }
        System.out.println("before emlpo");

        for(Employee employee:employeebyname)
        {
            System.out.println(employee);
            employeeList.add(employee);
        }

        System.out.println("after emlpo");

        for(Employee employee:this.getEmployeePagination(pageNumber,pageSize,employeeList))
        {
            System.out.println("1  " + employee);
            EmployeeDTO employeeDTO=parseEmployee(employee);
            employeeDTOS.add(employeeDTO);
            System.out.println(employee);
        }
        return employeeDTOS;

//        return employees;
    }

    @Transactional
    public Set<EmployeeDTO> getAllEmployeesSortedByName(Integer pageNumber, Integer pageSize) {
        Set <EmployeeDTO> allEmployeeDTOS=new LinkedHashSet<>();
        List <Employee> allEmployees = employeeRepository.findAll();

        LinkedHashSet<Employee> allEmployeeSorted= allEmployees.stream().collect(Collectors.toCollection(LinkedHashSet::new));
        for(Employee e:getSortedAllEmployeePagination(pageNumber,pageSize,allEmployeeSorted,"name"))
        {
            allEmployeeDTOS.add(parseEmployee(e));
        }
        return allEmployeeDTOS;

    }

    @Transactional
    public Set<EmployeeDTO> getAllEmployeesSortedByDate(Integer pageNumber, Integer pageSize) {
        Set <EmployeeDTO> allEmployeeDTOS=new LinkedHashSet<>();
        List <Employee> allEmployees = employeeRepository.findAll();

        LinkedHashSet<Employee> allEmployeeSorted= allEmployees.stream().collect(Collectors.toCollection(LinkedHashSet::new));

        for(Employee e:getSortedAllEmployeePagination(pageNumber,pageSize,allEmployeeSorted,"date"))
        {
            allEmployeeDTOS.add(parseEmployee(e));
        }
        return allEmployeeDTOS;

    }


    public ResponseEntity<byte[]> getVideo(String username) throws IOException {

        System.out.println(username);
        byte[] bytes = Files.readAllBytes(Paths.get("src/main/resources/employee_videos/"+username+".mp4"));
        System.out.println(bytes.length);
        HttpHeaders headers=new HttpHeaders();
        headers.set("Content-Type", "video/mp4");
        headers.setContentLength(bytes.length);
        return  new ResponseEntity<byte[]>(bytes,headers, HttpStatus.OK);

    }
    private List<Employee> getSortedAllEmployeePagination(Integer pageNumber,Integer pageSize,Set<Employee> employees,
                                                          String parameter) {
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

}