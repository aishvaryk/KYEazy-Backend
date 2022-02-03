package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.dto.JwtRequest;
import com.hashedin.product.kyeazy.dto.JwtResponse;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import com.hashedin.product.kyeazy.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private CompanyService companyService;
    @Autowired
    private  EmployeeService employeeService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService  customUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<JwtResponse> token( JwtRequest jwtRequest) throws Exception
    {
        int userId=0;
        String username="";
        if(jwtRequest.getRole().equals("ADMIN"))
        {
            if(!jwtRequest.getUsername().equals("admin")) throw new Exception("Wrong Username!");
            if(! jwtRequest.getPassword().equals("admin")) throw new Exception("Invalid Credentials!");
            username+="A"+jwtRequest.getUsername();
        }
        if(jwtRequest.getRole().equals("COMPANY"))
        {
            Company company =companyService.getCompanyByUsername(jwtRequest.getUsername());
            if(company==null) throw new Exception("Wrong Username!");
            if(! (company.getPassword().equals(jwtRequest.getPassword()))) throw new Exception("Invalid Credentials!");
            userId=company.getCompanyId();
            username+="C"+jwtRequest.getUsername();
        }
        if(jwtRequest.getRole().equals("EMPLOYEE"))
        {
            Employee employee =employeeService.getEmployeeByUsername(jwtRequest.getUsername());
            if(employee==null) throw new Exception("Wrong Username!");
            userId=employee.getEmployeeId();
            if(! (employee.getPassword().equals(jwtRequest.getPassword()))) throw new Exception("Invalid Credentials!");
            username+="E"+jwtRequest.getUsername();
        }
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,jwtRequest.getPassword()));
        UserDetails userDetails=this.customUserDetailsService.loadUserByUsername(username);
        String token=this.jwtUtil.generateToken(userDetails);
            return  ResponseEntity.ok(new JwtResponse(token,userId));
    }

}
