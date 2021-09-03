package com.hashedin.product.kyeazy.repositories;

import com.hashedin.product.kyeazy.entities.Admin;
import com.hashedin.product.kyeazy.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
