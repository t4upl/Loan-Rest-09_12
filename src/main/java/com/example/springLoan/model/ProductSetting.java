package com.example.springLoan.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_setting", schema = "public")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString (exclude = {"product", "setting"})
public class ProductSetting implements Serializable {

    private static final long serialVersionUID = 1209700235249323129L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;
}