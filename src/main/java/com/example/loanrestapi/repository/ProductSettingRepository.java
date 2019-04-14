package com.example.loanrestapi.repository;

import com.example.loanrestapi.model.ProductSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSettingRepository extends JpaRepository<ProductSetting, Long> {
}
