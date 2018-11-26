package com.example.springLoan_18112018.service;

import com.example.springLoan_18112018.model.Product;
import com.example.springLoan_18112018.other.ClientDataWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface ProductService {

    @Transactional
    Product applyForLoan(ClientDataWrapper clientDataWrapper);
}
