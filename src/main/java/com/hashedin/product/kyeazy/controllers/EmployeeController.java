package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PatchMapping("/update-profile")
    public ActionDTO updateProfile(@RequestBody Employee employee)
    {
        return  employeeService.updateProfileData(employee);
    }

    @PatchMapping(value="/update-captured-image/{id}",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO updateCapturedImage(@PathVariable Integer id, @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException
    {
        return  employeeService.updateEmployeeImage(id,profilePicture);
    }

    @                                                                                                                                           PatchMapping(value="/update-video/{id}",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO updateVideo(@PathVariable Integer id, @RequestParam("employeeVideo") MultipartFile employeeVideo) throws IOException
    {
        return  employeeService.updateEmployeeVideo(id,employeeVideo);
    }

    @PatchMapping(value="/update-document/{id}",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO updateDocument(@PathVariable Integer id, @RequestParam("employeeDocument") MultipartFile employeeVideo) throws IOException
    {
        return  employeeService.updateEmployeeDocument(id,employeeVideo);
    }

    @PatchMapping(value="/update-status/{id}")
    public ActionDTO updateStatus(@PathVariable Integer id)
    {
        return  employeeService.updateEmployeeStatus(id);
    }

    @GetMapping("/view-profile/{employeeId}")
    public Employee viewProfile(@PathVariable Integer employeeId)
    {
        return  employeeService.getEmployeeData(employeeId);
    }

}
