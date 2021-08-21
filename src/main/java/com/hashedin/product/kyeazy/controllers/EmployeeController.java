package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/login")
    public ActionDTO login()
    {
        return null;
    }

    @RequestMapping("/logout")
    public ActionDTO logout()
    {
        return null;
    }

    @PatchMapping(value="/update-profile/{id}/profile-pictures",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO updateProfileImage(@PathVariable Integer id, @RequestParam("profilePicture") MultipartFile profilePicture) throws IOException
    {
        return  employeeService.updateEmployeeImage(id,profilePicture);
    }

    @PatchMapping(value="/update-video/{id}/video",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ActionDTO updateVideo(@PathVariable Integer id, @RequestParam("employeeVideo") MultipartFile employeeVideo) throws IOException
    {
        return  employeeService.updateEmployeeVideo(id,employeeVideo);
    }

    @PatchMapping("/update-profile")
    public ActionDTO updateProfile(@RequestBody  Employee employee)
    {
        return  employeeService.updateProfileData(employee);
    }
    @GetMapping("/view-profile/{employeeId}")
    public Employee viewProfile(@PathVariable Integer employeeId)
    {
        return  employeeService.getEmployeeData(employeeId);
    }
}
