package com.journaldev.dao;

import com.journaldev.entity.Product;


public interface ProductDAO extends GenericInsertableDAO<Product> {
    Product insert(Product product);
    Long getCount();
}
