package com.hashedin.product.kyeazy.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="company_order")
@Getter
@Setter
public class CompanyOrder {

    @Id
    @Column(name="order_unique_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderUniqueId;


    @Column(name="order_id")
    private String orderId;

    @Column(name="payment_id")
    private String paymentId;

    @Column(name="amount")
    private Integer amount;

    @Column(name="company_order_id")
    private Integer companyOrderId;

}
