package com.example.springLoan_18112018.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_setting", schema = "public")
@Getter @Setter
@ToString (exclude = {"product", "setting"})
public class ProductSetting implements Serializable {

    @Id
    @Column(columnDefinition = "id")
    private Integer id;

    @Column(columnDefinition = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="setting_id")
    private Setting setting;

}
