package com.hashedin.product.kyeazy.repositories;
import com.hashedin.product.kyeazy.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer>, JpaSpecificationExecutor<Employee> {
    public List<Employee> findAllByCompanyId(Integer companyId,Pageable  pageable);
    public List<Employee> findAllByCompanyId(Integer companyId);

    public List<Employee> findAllByStatus(String status,Pageable pageable);
    public List<Employee> findAllByStatus(String status);

    public List<Employee> findAllByStatusAndCompanyId(String status, Integer companyId, Pageable pageable);
    public List<Employee> findAllByStatusAndCompanyId(String status, Integer companyId);

    public List<Employee> findAllByDisplayNameStartingWithAndStatus(String displayName,String status, Pageable  pageable);
    public List<Employee> findAllByDisplayNameStartingWithAndStatus(String displayName,String status);


    public List<Employee> findAllByDisplayNameStartingWith(String displayName, Pageable  pageable);
    public List<Employee> findAllByDisplayNameStartingWith(String displayName);


    public List<Employee> findAllByDisplayNameStartingWithAndCompanyId(String firstName,Integer companyId, Pageable  pageable);
    public List<Employee> findAllByDisplayNameStartingWithAndCompanyId(String firstName,Integer companyId);

    public List<Employee> findAllByDisplayNameStartingWithAndCompanyIdAndStatus(String firstName,Integer companyId,String filter);
    public List<Employee> findAllByDisplayNameStartingWithAndCompanyIdAndStatus(String firstName,Integer companyId,String filter, Pageable pageable);

}
