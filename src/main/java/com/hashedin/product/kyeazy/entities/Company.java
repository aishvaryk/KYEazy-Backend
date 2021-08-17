package com.hashedin.product.kyeazy.entities;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="company")
@Getter @Setter
public class Company {

    @Id
    @Column(name="company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="name")
    private String name;

    @Column(name="company_description")
    private String companyDescription;

    @Column(name="cin_number")
    private String CINNumber;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="addressId")
    private  Address address;

    @OneToMany(mappedBy = "company")
    private Set<Employee> employees = new HashSet<>();


}
