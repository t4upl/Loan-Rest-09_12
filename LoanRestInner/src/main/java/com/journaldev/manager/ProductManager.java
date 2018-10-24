package com.journaldev.manager;

import com.journaldev.other.ClientDataWrapper;
import org.springframework.transaction.annotation.Transactional;

public interface ProductManager {

    @Transactional
    void takeLoan(ClientDataWrapper clientDataWrapper);
}
