package com.journaldev.dao;

import com.journaldev.entity.Product;


public interface ProductDAO extends GenericInsertableDAO<Product> {
    Product insert(Product product);
    Product findById(int id);
    Long getCount();
    Product insertOrUpdate(Product product);
}
