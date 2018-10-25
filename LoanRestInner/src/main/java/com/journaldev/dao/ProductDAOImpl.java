package com.journaldev.dao;

import com.journaldev.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDAOImpl extends AbstractDAOImpl<Product> implements ProductDAO {

    public ProductDAOImpl() {
        super(Product.class);
    }

    @Override
    public Product insert(Product product) {
        SessionObject sessionObject = getSesionTransactionObject();
        sessionObject.getSession().save(product);
        sessionObject.commitTransactionAndCloseSession();
        return product;
    }
}
