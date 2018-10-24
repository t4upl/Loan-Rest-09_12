package com.journaldev.dao;


import com.journaldev.entity.Customer;

public class CustomerDAOImpl extends AbstractDAO implements CustomerDAO {

    public CustomerDAOImpl() {
        super(Customer.class);
    }

    @Override
    public Customer findById(int id) {
        SessionObject sessionObject = getSesionTransactionObject();
        Customer customer = (Customer) sessionObject.getSession().get(entityClass, id);
        sessionObject.commitTransactionAndCloseSession();
        return customer;
    }
}
