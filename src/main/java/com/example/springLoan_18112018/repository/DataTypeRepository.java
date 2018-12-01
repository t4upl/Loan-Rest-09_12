package com.example.springLoan_18112018.repository;


import com.example.springLoan_18112018.model.DataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTypeRepository extends JpaRepository<DataType, Integer> {
}
