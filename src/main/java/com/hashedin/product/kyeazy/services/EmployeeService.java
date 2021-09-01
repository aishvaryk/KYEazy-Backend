package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Company;
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

    public ActionDTO updateProfileData(Employee employeeDetails)
    {
        Employee employee=this.getEmployeeData(employeeDetails.getEmployeeId());
        employee.setDocumentNumber(employeeDetails.getDocumentNumber());
        employee.setDocumentType(employeeDetails.getDocumentType());
        employee.setAddress(employeeDetails.getAddress());
        employee.setStatus("Pending");
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
       //System.out.println("Current folder: " + (new File(".")).getCanonicalPath());
        //ClassLoader classLoader = getClass().getClassLoader();
        Employee employee=this.getEmployeeData(employeeId);
        //File file = new File(classLoader.getResource(".").getFile() + "summary.txt");
        String uploadDir="src/main/resources/employee_videos";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFileName=profileVideo.getOriginalFilename();
       String extension = originalFileName.substring(originalFileName.indexOf('.'),originalFileName.length());
       //System.out.println(extension);
        try (InputStream inputStream = profileVideo.getInputStream()) {

            Path filePath = uploadPath.resolve(employee.getUsername()+extension);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + "Help", ioe);
        }


        return new ActionDTO(1,true,"Employee Details Added Successfully.");
    }
    public ActionDTO updateEmployeeDocument(Integer employeeId, MultipartFile document) throws IOException
    {
        //System.out.println("Current folder: " + (new File(".")).getCanonicalPath());
        //ClassLoader classLoader = getClass().getClassLoader();
        Employee employee=this.getEmployeeData(employeeId);
        //File file = new File(classLoader.getResource(".").getFile() + "summary.txt");
        String uploadDir="src/main/resources/employee_documents";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String originalFileName=document.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.indexOf('.'),originalFileName.length());
        //System.out.println(extension);
        try (InputStream inputStream = document.getInputStream()) {

            Path filePath = uploadPath.resolve(employee.getUsername()+extension);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + "Help", ioe);
        }


        return new ActionDTO(1,true,"Employee Details Added Successfully.");
    }
    @Transactional
    public Employee getEmployeeData(Integer employeeId) {

        return employeeRepository.findById(employeeId).get();

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
