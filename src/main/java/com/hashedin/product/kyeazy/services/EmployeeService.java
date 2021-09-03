package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Employee getEmployeeData(Integer employeeId)
    {
        return employeeRepository.findById(employeeId).get();
    }

    public ActionDTO updateProfileData(Employee employeeDetails)
    {
        Employee employee=this.getEmployeeData(employeeDetails.getEmployeeId());
        employee.setDocumentNumber(employeeDetails.getDocumentNumber());
        employee.setDocumentType(employeeDetails.getDocumentType());
        employee.setAddress(employeeDetails.getAddress());
        employee.setGender(employeeDetails.getGender());
        employee.setDisplayName(employeeDetails.getFirstName()+" "+employee.getLastName());
        Employee savedEmployee = employeeRepository.save(employee);
        return new ActionDTO(savedEmployee.getEmployeeId(),true,"Employee Details Added Successfully.");
    }

    public ActionDTO updateEmployeeImage(Integer employeeId, MultipartFile profilePicture) throws IOException
    {
        Employee employee=this.getEmployeeData(employeeId);
        employee.setCapturedImage(profilePicture.getBytes());
        Employee savedEmployee = employeeRepository.save(employee);
        return new ActionDTO(savedEmployee.getEmployeeId(),true,"Employee Details Added Successfully.");
    }

    public ActionDTO updateEmployeeVideo(Integer employeeId, MultipartFile profileVideo) throws IOException
    {
        Employee employee=this.getEmployeeData(employeeId);
        String uploadDir="src/main/resources/employee_videos";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFileName=profileVideo.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.indexOf('.'),originalFileName.length());
        InputStream inputStream = profileVideo.getInputStream();
        Path filePath = uploadPath.resolve(employee.getUsername()+extension);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        return new ActionDTO(employeeId,true,"Employee Details Added Successfully.");
    }

    public ActionDTO updateEmployeeDocument(Integer employeeId, MultipartFile document) throws IOException
    {
        Employee employee=this.getEmployeeData(employeeId);
        String uploadDir="src/main/resources/employee_documents";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFileName=document.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.indexOf('.'),originalFileName.length());
        InputStream inputStream = document.getInputStream();
        Path filePath = uploadPath.resolve(employee.getUsername()+extension);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        return new ActionDTO(employeeId,true,"Employee Details Added Successfully.");
    }

    public ActionDTO updateEmployeeStatus(Integer employeeId)
    {
        Employee employee=getEmployeeData(employeeId);
        employee.setStatus("Pending");
        Employee savedEmployee = employeeRepository.save(employee);
        return new ActionDTO(savedEmployee.getEmployeeId(),true,"Employee Details Added Successfully.");
    }

    @Transactional
    public Employee getEmployeeByUsername(String userName) {
        List<Employee> employees=employeeRepository.findAll();
        for(Employee employeeToCheck:employees) {
            if(employeeToCheck.getUsername().equals(userName))
            {
                return employeeToCheck;
            }

        }
        return null;
    }


}
