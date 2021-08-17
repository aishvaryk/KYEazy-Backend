package com.hashedin.product.kyeazy.entities;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name="address")
@Getter
@Setter
public class Address {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="address_id")
        private int addressId;

        @Column(name="street_number")
        private String streetNumber;

        @Column(name="street")
        private String street;

        @Column(name="city")
        private String city;

        @Column(name="pin_code")
        private String pinCode;

        @Column(name="state")
        private String state;

        @Column(name="country")
        private String country;

        @Column(name="type")
        private String type;

}

