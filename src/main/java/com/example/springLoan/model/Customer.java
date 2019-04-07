package com.example.springLoan.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "customer", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@ToString (exclude = {"products"})
public class Customer implements Serializable {

    private static final long serialVersionUID = -5027056122837499602L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "customer")
    Set<Product> products;

}
