package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.JwtRequest;
import com.hashedin.product.kyeazy.dto.JwtResponse;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.exceptions.response.ExceptionResponse;
import com.hashedin.product.kyeazy.jwt.config.util.JwtUtil;
import com.hashedin.product.kyeazy.services.CompanyService;
import com.hashedin.product.kyeazy.services.CustomUserDetailsService;
import com.hashedin.product.kyeazy.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private CustomUserDetailsService  customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(value="/token",method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception
    {
        int userId=0;
        System.out.println(jwtRequest.getRole());
        String username="";
        if(jwtRequest.getRole().equals("ADMIN"))
        {
            if(!jwtRequest.getUsername().equals("Riya")) throw new Exception("Wrong Username!");
            if(! jwtRequest.getPassword().equals("Riya123")) throw new Exception("Invalid Credentials!");

            username+="A"+jwtRequest.getUsername();
        }
        if(jwtRequest.getRole().equals("COMPANY"))
        {
            Company company =companyService.getCompanyByUsername(jwtRequest.getUsername());
            if(company==null) throw new Exception("Wrong Username!");
            if(! (company.getPassword().equals(jwtRequest.getPassword()))) throw new Exception("Invalid Credentials!");
            userId=company.getCompanyId();
            username+="C"+jwtRequest.getUsername();
            System.out.println("usernaeme"+username);
        }
        if(jwtRequest.getRole().equals("EMPLOYEE"))
        {

            Employee employee =employeeService.getEmployeeByUsername(jwtRequest.getUsername());
            if(employee==null) throw new Exception("Wrong Username!");
            userId=employee.getEmployeeId();
            System.out.println(jwtRequest.getPassword());
            if(! (employee.getPassword().equals(jwtRequest.getPassword()))) throw new Exception("Invalid Credentials!");
            username+="E"+jwtRequest.getUsername();
        }
        System.out.println(jwtRequest);
        System.out.println("_________"+username);

        try{
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,jwtRequest.getPassword()));
    }

    catch(Exception e)
    {
     throw e;
    }
    System.out.println("_________"+username);
        UserDetails userDetails=this.customUserDetailsService.loadUserByUsername(username);
        String token=this.jwtUtil.generateToken(userDetails);
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token,userId));
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception exc) {
        ExceptionResponse error = new ExceptionResponse();
        error.setStatus(HttpStatus.LENGTH_REQUIRED.value());
        error.setMessage(exc.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.LENGTH_REQUIRED);

    }
}
