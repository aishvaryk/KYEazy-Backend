package com.hashedin.product.kyeazy.repositories;

import com.hashedin.product.kyeazy.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
}