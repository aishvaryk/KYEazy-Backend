package com.hashedin.product.kyeazy.services;
import com.hashedin.product.kyeazy.dto.CompanyDTO;
import com.hashedin.product.kyeazy.dto.EmployeeDTO;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.CompanyRepository;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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
    @Transactional
    public List<EmployeeDTO> viewAllApplications(Integer pageNumber,Integer pageSize)
    {
        List<Employee> employees=employeeRepository.findAll();

        List<EmployeeDTO> employeeDTOS=new LinkedList<>();
        Set<Employee> employeeList=new HashSet<>();
        for(Employee employee:employees)
        {
            employeeList.add(employee);
        }
        for(Employee employee:this.getEmployeePagination(pageNumber,pageSize,employeeList))
        {
            EmployeeDTO employeeDTO=parseEmployee(employee);
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }
    @Transactional
    public Set<EmployeeDTO> viewPendingApplications(Integer pageNumber,Integer pageSize)
    {
        Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        List<Employee> employee=employeeRepository.findAll();
        Set<Employee> pendingApplications = employee.stream().filter(p->{ return p.getStatus().equalsIgnoreCase("Pending");}).collect(Collectors.toSet());
        for(Employee e:this.getEmployeePagination(pageNumber,pageSize,pendingApplications))
        {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
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
    public List<CompanyDTO> getCompanies(Integer pageNumber,Integer pageSize)
    {
        List<Company> companies=companyRepository.findAll();
        Set<Company> companySet= companies.stream().collect(Collectors.toSet());

        List<CompanyDTO> companyDTOS=new LinkedList<>();

        for(Company company:this.getCompanyPagination(pageNumber,pageSize,companySet))
        {
            companyDTOS.add(parseCompany(company));
        }

        return  companyDTOS;
    }

    @Transactional
    public Set<EmployeeDTO> viewAcceptedApplications(Integer pageNumber,Integer pageSize)
    {
        Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        List<Employee> employee=employeeRepository.findAll();

        Set<Employee> acceptedApplications = employee.stream().filter(p->{ return p.getStatus().equalsIgnoreCase("Accepted");}).collect(Collectors.toSet());
        for(Employee e:this.getEmployeePagination(pageNumber,pageSize,acceptedApplications))
        {
            employeeDTOS.add(parseEmployee(e));
        }
        return employeeDTOS;
    }
    @Transactional
    public Set<EmployeeDTO> viewRejectedApplications(Integer pageNumber,Integer pageSize)
    {   Set<EmployeeDTO> employeeDTOS=new HashSet<>();
        List<Employee> employee=employeeRepository.findAll();

        Set<Employee> rejectedApplications = employee.stream().filter(p->{ return p.getStatus().equalsIgnoreCase("Rejected");}).collect(Collectors.toSet());
        for(Employee e:this.getEmployeePagination(pageNumber,pageSize,rejectedApplications))
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
        Employee employee=getEmployeeById(employeeId);
        byte[] profilePictureBytes = employee.getCapturedImage();
        return profilePictureBytes;
    }

    private List<Company> getCompanyPagination(Integer pageNumber,Integer pageSize,Set<Company> companies)
    {

        Integer lastIndex=companies.size();
        Integer from=(pageNumber-1)*pageSize;
        Integer to=from+pageSize;
        if(from>companies.size()-1) return null;
        if(to>lastIndex) to=lastIndex;
        List<Company> companyList=new ArrayList<>();

        companies.stream().sorted(Comparator.comparing(Company::getCompanyId)).forEach((p)->{
            companyList.add(p);
        });

        return companyList.subList(from,to);

    }
    private CompanyDTO parseCompany(Company company)
    {
        CompanyDTO companyDTO=new CompanyDTO();
        companyDTO.setEmployees(company.getEmployees());
        companyDTO.setCompanyId(company.getCompanyId());
        companyDTO.setCompanyDescription(company.getCompanyDescription());
        companyDTO.setName(company.getName());
        companyDTO.setCINNumber(company.getCinNumber());
        companyDTO.setUsername(company.getUsername());
        companyDTO.setAddress(companyDTO.getAddress());
        return  companyDTO;
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
        employeeDTO.setCapturedImage(employee.getCapturedImage());
        //  employeeDTO.setCapturedImage(employee.getCapturedImage());
        return  employeeDTO;
    }
    @Transactional
    private Company getCompanyById(Integer companyId)
    {
        return companyRepository.findById(companyId).get();
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
}
