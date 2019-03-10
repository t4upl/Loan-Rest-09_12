package com.example.springLoan.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_setting", schema = "public")
@Getter @Setter
@ToString (exclude = {"product", "setting"})
@AllArgsConstructor
@NoArgsConstructor
public class ProductSetting implements Serializable {

    @Id
    @Column(columnDefinition = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
