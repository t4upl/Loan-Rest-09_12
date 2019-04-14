package com.example.loanrestapi.repository;

import com.example.loanrestapi.model.ProductTypeSetting;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeSettingRepository extends JpaRepository<ProductTypeSetting, Integer> {

  List<ProductTypeSetting> findByProductType_Id(Long productTypeId);
}
