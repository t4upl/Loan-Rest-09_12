package com.example.loanrestapi.model;

import com.example.loanrestapi.enums.SettingName;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "setting", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"dataType"})
@EqualsAndHashCode
public class Setting implements Serializable {

  private static final long serialVersionUID = 293926029277452652L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  private SettingName name;

  @ManyToOne
  @JoinColumn(name = "data_type_id")
  private DataType dataType;

  @Column(name = "is_runtime_input")
  private Boolean isRuntimeInput;

  @OneToMany(mappedBy = "setting")
  private Set<ProductTypeSetting> productTypeSettings;


  @OneToMany(mappedBy = "setting")
  private Set<ProductSetting> productSettings;


}
