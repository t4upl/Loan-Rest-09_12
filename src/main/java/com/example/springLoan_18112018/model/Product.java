package com.example.springLoan_18112018.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JoinColumn(name="product_type_id")
    @JsonIgnore
    private ProductType productType;

}
