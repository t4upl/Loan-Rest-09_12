package com.journaldev.playground.wiki;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name="Employee")
public class Employee {
    @Id
    @Column(name="id")
    private int id;

    @OneToMany(mappedBy="owner", fetch = FetchType.EAGER)
    private List<Phone> phones;

}
