package com.journaldev.playground.wiki;

import com.journaldev.dao.AbstractDAOImpl;

public class EmployeeDAO extends AbstractDAOImpl {

    public EmployeeDAO() {
        super(Employee.class);
    }

    public Employee findById(int id) {
        SessionObject sessionObject = getSesionTransactionObject();
        Employee employee = (Employee) sessionObject.getSession().get(entityClass, 1);
        sessionObject.commitTransactionAndCloseSession();
        return employee;
    }


}
