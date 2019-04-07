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

    private static final long serialVersionUID = 2524934507480166707L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @ManyToOne
    @JoinColumn(name = "setting_id")
    private Setting setting;

}
