package com.example.springLoan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "setting", schema = "public")
@Getter
@Setter
@ToString (exclude = {"dataType"})
@EqualsAndHashCode
public class Setting {
    @Id
    @Column(columnDefinition = "id")
    private Integer id;

    @Column(columnDefinition = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name="data_type_id")
    @JsonIgnore
    private DataType dataType;

    @Column(name = "is_runtime_input")
    private Boolean isRuntimeInput;

    @OneToMany(mappedBy = "setting")
    private Set<ProductTypeSetting> productTypeSettings;

    @OneToMany(mappedBy = "setting")
    private Set<ProductSetting> productSettings;
}
