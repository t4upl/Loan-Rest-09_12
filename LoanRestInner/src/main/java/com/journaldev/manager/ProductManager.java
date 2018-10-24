package com.journaldev.manager;

import com.journaldev.entity.Product;
import com.journaldev.other.ClientDataWrapper;
import org.springframework.transaction.annotation.Transactional;

public interface ProductManager {

    @Transactional
    Product applyForLoan(ClientDataWrapper clientDataWrapper);
}
