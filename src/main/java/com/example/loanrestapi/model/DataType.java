package com.example.loanrestapi.model;

import com.example.loanrestapi.enums.DataTypeEnum;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "data_type", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
