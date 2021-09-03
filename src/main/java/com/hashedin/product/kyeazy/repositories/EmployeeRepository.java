package com.hashedin.product.kyeazy.repositories;

import com.hashedin.product.kyeazy.entities.Company;
import com.hashedin.product.kyeazy.entities.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    public List<Employee> findAllByCompanyId(Integer companyId,Pageable  pageable);
    public List<Employee> findAllByDisplayNameStartingWithAndCompanyId(String displayName,Integer companyId, Pageable  pageable);
    public List<Employee> findAllEmployeesByStatus(String status);

    public List<Employee> findAllByStatusAndCompanyId(String status, Integer companyId, Pageable pageable);

}
