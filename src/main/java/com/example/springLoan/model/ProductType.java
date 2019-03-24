package com.example.springLoan.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product_type", schema = "public")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductType {
    @Id
    @Column(columnDefinition = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "name")
    private String name;

    @OneToMany(mappedBy = "productType")
    Set<Product> products;

    @OneToMany(mappedBy = "productType")
    Set<ProductTypeSetting> productTypeSettings;
}
