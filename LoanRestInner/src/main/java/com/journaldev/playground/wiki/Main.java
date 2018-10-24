package com.journaldev.playground.wiki;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        EmployeeDAO employeeDAO   = (EmployeeDAO) context.getBean("EmployeeDAO");
        Employee employee = employeeDAO.findById(1);
        System.out.println(employee);
        System.out.println(Arrays.toString(employee.getPhones().toArray()));


    }
}
