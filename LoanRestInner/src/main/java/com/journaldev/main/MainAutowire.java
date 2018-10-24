package com.journaldev.main;

import com.journaldev.dao.CustomerDAO;
import com.journaldev.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainAutowire {

    public CustomerDAO customerDAO;

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        MainAutowire main = (MainAutowire)context.getBean("mainAutowire");
        Customer customer = main.customerDAO.findById(1);
        System.out.println(customer);

    }
}
