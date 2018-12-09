package com.journaldev.entity;


import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "setting_type", schema = "public")
@ToString
@EqualsAndHashCode
public class SettingType {

    private int id;
    private String name;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<ProductTypeSetting >productTypeSettings;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<ProductSetting> productSettings;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy="settingType", fetch = FetchType.EAGER)
    public Set<ProductTypeSetting> getProductTypeSettings() {
        return productTypeSettings;
    }

    public void setProductTypeSettings(Set<ProductTypeSetting> productTypeSettings) {
        this.productTypeSettings = productTypeSettings;
    }

    @OneToMany(mappedBy="settingType", fetch = FetchType.EAGER)
    public Set<ProductSetting> getProductSettings() {
        return productSettings;
    }

    public void setProductSettings(Set<ProductSetting> productSettings) {
        this.productSettings = productSettings;
    }
}
