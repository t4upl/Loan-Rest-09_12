package com.example.springLoan_18112018.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Integer id;

    @Column(columnDefinition = "name")
    private String name;

    @OneToMany(mappedBy = "productType")
    Set<Product> products;
}
