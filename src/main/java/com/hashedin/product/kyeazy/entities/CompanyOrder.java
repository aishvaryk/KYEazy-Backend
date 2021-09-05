package com.hashedin.product.kyeazy.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="company_order")
@Getter
@Setter
public class CompanyOrder {

    @Id
    @Column(name="order_id")
    private String orderId;

    @Column(name="payment_id")
    private String paymentId;

    @Column(name="amount")
    private Integer amount;

    @Column(name="company_order_id")
    private Integer companyOrderId;

}
