package com.hashedin.product.kyeazy.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import  com.hashedin.product.kyeazy.repositories.*;

@RunWith(MockitoJUnitRunner.class)
public class CompanyServiceTests {

    @InjectMocks
    CompanyService companyService;

    @Mock
    CompanyRepository companyRepository;
    @Test
    public void shouldGetCompanyById() {
    }



}
