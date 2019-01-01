package com.example.springLoan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product", schema = "public")
@Getter
@Setter
@ToString (exclude = {"customer", "productType"})
public class Product {

    @Id
    @Column(columnDefinition = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "product")
    Set<ProductSetting> productSettings;
}
