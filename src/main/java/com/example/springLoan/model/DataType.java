package com.example.springLoan.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "data_type", schema = "public")
@Getter @Setter
@ToString
public class DataType implements Serializable {

    @Id
    @Column(columnDefinition = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "name")
    private String name;

    @OneToMany(mappedBy = "dataType")
    Set<Setting> dataTypes;

}
