package com.hashedin.product.kyeazy.entities;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="admin")
@Getter @Setter
public class Admin
{
    @Id
    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

}
