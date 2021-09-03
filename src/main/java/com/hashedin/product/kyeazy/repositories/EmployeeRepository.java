package com.hashedin.product.kyeazy.repositories;

import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    List<Employee> findAllByCompanyId(int companyId, Pageable pageable);
    List<Employee> findAllEmployeesByStatus(String status);

}
