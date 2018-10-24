package com.journaldev.playground.wiki;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@Table(name="Phone")
@ToString
public class Phone {
    @Id
    @Column(name="id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "OWNER_ID")
    @ToString.Exclude
    private Employee owner;
}
