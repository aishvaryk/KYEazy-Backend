package com.hashedin.product.kyeazy.services;

import com.hashedin.product.kyeazy.entities.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CompanyService companyService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
             Company company=companyService.getCompanyByUsername(userName);
            return new User(company.getUsername(), company.getPassword(), new ArrayList<>());


    }
}
