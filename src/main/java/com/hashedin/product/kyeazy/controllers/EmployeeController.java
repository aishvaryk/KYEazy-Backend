package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

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

    @RequestMapping("/submit")
    public ActionDTO submit()
    {
        return null;
    }

    @RequestMapping("/update-profile")
    public ActionDTO updateProfile()
    {
        return null;
    }
}
