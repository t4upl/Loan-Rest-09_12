package com.example.springLoan.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "product_type", schema = "public")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductType implements Serializable {

    private static final long serialVersionUID = -2538214832373421471L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "productType")
    Set<Product> products;

    @OneToMany(mappedBy = "productType")
    Set<ProductTypeSetting> productTypeSettings;
}
