package com.example.loanrestapi.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product_type", schema = "public")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductType implements Serializable {

  private static final long serialVersionUID = -2538214832373421471L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "productType")
  Set<Product> products;

  @OneToMany(mappedBy = "productType")
  Set<ProductTypeSetting> productTypeSettings;
}
