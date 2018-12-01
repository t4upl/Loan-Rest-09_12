package com.example.springLoan_18112018.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "product_type_setting", schema = "public")
@Getter @Setter
@ToString (exclude = {"productType", "setting"})
public class ProductTypeSetting implements Serializable {

    @Id
    @Column(columnDefinition = "id")
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
