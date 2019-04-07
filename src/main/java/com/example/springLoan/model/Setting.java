package com.example.springLoan.model;

import com.example.springLoan.enums.DataTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "setting", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString (exclude = {"dataType"})
@EqualsAndHashCode
public class Setting implements Serializable {

    private static final long serialVersionUID = 293926029277452652L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "is_runtime_input")
    private Boolean isRuntimeInput;

    @OneToMany(mappedBy = "setting")
    private Set<ProductTypeSetting> productTypeSettings;

    @OneToMany(mappedBy = "setting")
    private Set<ProductSetting> productSettings;

    @ManyToOne
    @JoinColumn(name="data_type_id")
    private DataType dataType;
}
