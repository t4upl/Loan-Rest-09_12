package com.example.springLoan.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "data_type", schema = "public")
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DataType implements Serializable {

    @Id
    @Column(columnDefinition = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "name")
    private String name;

    @OneToMany(mappedBy = "dataType")
    Set<Setting> settings;

}
