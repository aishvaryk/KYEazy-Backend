package com.hashedin.product.kyeazy.controllers;

import com.hashedin.product.kyeazy.dto.JwtRequest;
import com.hashedin.product.kyeazy.dto.JwtResponse;
import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.exceptions.response.ExceptionResponse;
import com.hashedin.product.kyeazy.jwt.config.util.JwtUtil;
import com.hashedin.product.kyeazy.services.CompanyService;
import com.hashedin.product.kyeazy.services.CustomUserDetailsService;
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
    private AuthenticationManager authenticationManager;

    @RequestMapping(value="/token",method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception
    {
        Company company =companyService.getCompanyByUsername(jwtRequest.getUsername());
        if(company==null) throw new Exception("Wrong Username!");
        if(!company.getPassword().equals(jwtRequest.getPassword())) throw new Exception("Invalid Credentials!");

        System.out.println(jwtRequest);

    try{
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
    }

    catch(Exception e)
    {
     throw e;
    }
        UserDetails userDetails=this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token=this.jwtUtil.generateToken(userDetails);
        System.out.println(token);
        return ResponseEntity.ok(new JwtResponse(token));
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
