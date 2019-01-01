package com.example.springLoan.repository;

import com.example.springLoan.model.ProductSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSettingRepository extends JpaRepository<ProductSetting, Integer> {
}
