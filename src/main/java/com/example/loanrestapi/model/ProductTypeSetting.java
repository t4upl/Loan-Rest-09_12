package com.example.loanrestapi.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product_type_setting", schema = "public")
@Getter
@Setter
@ToString(exclude = {"productType", "setting"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductTypeSetting implements Serializable {

  private static final long serialVersionUID = 2524934507480166707L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "value")
  private String value;

  @ManyToOne
  @JoinColumn(name = "product_type_id")
  private ProductType productType;

  @ManyToOne
  @JoinColumn(name = "setting_id")
  private Setting setting;

}
