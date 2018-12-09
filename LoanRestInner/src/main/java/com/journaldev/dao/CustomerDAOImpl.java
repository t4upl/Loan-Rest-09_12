package com.journaldev.dao;


import com.journaldev.entity.Customer;

public class CustomerDAOImpl extends AbstractDAOImpl<Customer> implements CustomerDAO {

    public CustomerDAOImpl() {
        super(Customer.class);
    }
}
