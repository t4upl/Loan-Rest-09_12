package com.example.springLoan_18112018.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer", schema = "public")
@Getter
@Setter
public class Customer {

    @Id
    @Column(columnDefinition = "id")
    private Integer id;

    @Column(columnDefinition = "name")
    private String name;

}
