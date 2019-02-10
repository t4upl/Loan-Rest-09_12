package com.example.springLoan.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_type_setting", schema = "public")
@Getter @Setter
@ToString (exclude = {"productType", "setting"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeSetting implements Serializable {

    @Id
    @Column(columnDefinition = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name="product_type_id")
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name="setting_id")
    private Setting setting;

}
