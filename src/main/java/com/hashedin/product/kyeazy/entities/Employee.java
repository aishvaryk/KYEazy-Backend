package com.hashedin.product.kyeazy.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="employee")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private Integer employeeId;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="contact_number")
    private String contactNumber;

    @Column(name="email_id")
    private String emailID;

    @Lob
    @Column(name="captured_image")
    private byte[] capturedImage;

    @Column(name="status")
    private String status;

    @Column(name="date_time_of_verification")
    private Date dateTimeOfVerification;

    /*
    @Column(name="test_video_path")
    private  String testVideoPath;
   */
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="addressId")
    private  Address address;

    @ManyToOne
    @MapsId("companyId")
    @JoinColumn(name="company_id")
    private Company company;


}
