package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.ActionDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

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

    @RequestMapping("/view-all-applications")
    public ActionDTO viewAllApplications()
    {
        return null;
    }

    @RequestMapping("/view-pending-applications")
    public ActionDTO viewPendingApplications()
    {
        return null;
    }

    @RequestMapping("/view-accepted-applications")
    public ActionDTO viewAcceptedApplications()
    {
        return null;
    }

    @RequestMapping("/view-rejected-applications")
    public ActionDTO viewRejectedApplications()
    {
        return null;
    }

    @RequestMapping("/verify")
    public ActionDTO verify()
    {
        return null;
    }

}
