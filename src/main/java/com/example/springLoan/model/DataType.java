package com.example.springLoan.model;

import com.example.springLoan.enums.DataTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "data_type", schema = "public")
@Getter
@Setter
@NoArgsConstructor
public class DataType implements Serializable {

    private static final long serialVersionUID = 2073516673766418828L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private DataTypeEnum name;

    @OneToMany(mappedBy = "dataType")
    Set<Setting> settings;

}
