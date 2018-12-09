package com.journaldev.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_type_setting")
@ToString
@EqualsAndHashCode
public class ProductTypeSetting {

    @EqualsAndHashCode.Exclude
    private Integer id;
    private Integer productTypeId;
    private Integer settingTypeId;
    private String value;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private SettingType settingType;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "product_type_id")
    public Integer getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Integer productTypeId) {
        this.productTypeId = productTypeId;
    }

    @Column(name = "setting_type_id")
    public Integer getSettingTypeId() {
        return settingTypeId;
    }

    public void setSettingTypeId(Integer settingTypeId) {
        this.settingTypeId = settingTypeId;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="setting_type_id", insertable= false, updatable =false)
    public SettingType getSettingType() {
        return settingType;
    }

    public void setSettingType(SettingType settingType) {
        this.settingType = settingType;
    }
}
