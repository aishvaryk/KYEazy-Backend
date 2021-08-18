package com.hashedin.product.kyeazy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class KyeazyApplication {

	public static void main(String[] args) {
		SpringApplication.run(KyeazyApplication.class, args);
	}

}
