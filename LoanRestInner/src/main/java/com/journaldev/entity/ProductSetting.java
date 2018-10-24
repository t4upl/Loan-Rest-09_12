package com.journaldev.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_setting")
@ToString
public class ProductSetting {

    int id;
    private int productId;
    private int settingTypeId;
    private String value;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Column(name = "setting_type_id")
    public int getSettingTypeId() {
        return settingTypeId;
    }

    public void setSettingTypeId(int settingTypeId) {
        this.settingTypeId = settingTypeId;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
