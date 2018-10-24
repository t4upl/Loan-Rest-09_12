package com.journaldev.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "product_setting", schema = "public")
@ToString
public class ProductSetting {

    Integer id;
    private int productId;
    private int settingTypeId;
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

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name="setting_type_id", referencedColumnName = "id", nullable = false,
                insertable= false, updatable =false)
    public SettingType getSettingType() {
        return settingType;
    }

    public void setSettingType(SettingType settingType) {
        this.settingType = settingType;
    }
}
