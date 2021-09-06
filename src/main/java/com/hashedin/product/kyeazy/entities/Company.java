package com.hashedin.product.kyeazy.entities;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.LinkedList;
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

    @Column(name="emailId")
    private String emailId;

    @Column(name="cin_number")
    private String cinNumber;

    @Column(name="coins")
    private Integer coins=200;

    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="addressId")
    private  Address address;

    @Column(name="plan")
    private Integer plan=10;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="company_order_id",referencedColumnName ="company_id" )
    private Set<CompanyOrder> orders = new HashSet<>();

    @Lob
    @Column(name="icon")
    private byte[] icon;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="company_id",referencedColumnName ="company_id" )
    private Set<Employee> employees = new HashSet<>();

}

