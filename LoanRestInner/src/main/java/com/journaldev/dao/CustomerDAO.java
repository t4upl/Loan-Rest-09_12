package com.journaldev.dao;


import com.journaldev.entity.Customer;

public interface CustomerDAO {
    Customer findById(int id);
}
