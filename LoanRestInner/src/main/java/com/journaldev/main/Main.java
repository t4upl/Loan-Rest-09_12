package com.journaldev.main;

import com.journaldev.dao.CustomerDAO;
import com.journaldev.entity.Customer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello");

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        CustomerDAO customerDAO = context.getBean(CustomerDAO.class);
        Customer customer = customerDAO.findById(1);
        System.out.println(customer);

    }
}
