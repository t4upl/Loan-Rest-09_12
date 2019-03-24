package com.example.springLoan.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "product", schema = "public")
@Getter
@Setter
@ToString (exclude = {"customer", "productType"})
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(columnDefinition = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    Set<ProductSetting> productSettings;
}
