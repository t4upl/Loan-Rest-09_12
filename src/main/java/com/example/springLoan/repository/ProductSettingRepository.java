package com.example.springLoan.repository;

import com.example.springLoan.model.ProductSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductSettingRepository extends JpaRepository<ProductSetting, Integer> {

//    Set<ProductSetting> saveAll(Set<ProductSetting> productSettings);
}
