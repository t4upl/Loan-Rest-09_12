package com.journaldev.dao;

import com.journaldev.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ProductDAOImpl extends AbstractDAOImpl implements ProductDAO {

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

    @Override
    public Product findById(int id) {
        SessionObject sessionObject = getSesionTransactionObject();
        Product product = (Product) sessionObject.getSession().get(entityClass, id);
        sessionObject.commitTransactionAndCloseSession();
        return product;
    }

//    @Override
//    public Long getCount() {
//        SessionObject sessionObject = getSesionTransactionObject();
//        Criteria criteria = sessionObject.getSession()
//                                         .createCriteria(entityClass)
//                                         .setProjection(Projections.rowCount());
//        Long rowCount = processCriteria(criteria);
//        sessionObject.commitTransactionAndCloseSession();
//        return rowCount;
//    }

    @Override
    @Transactional
    public Product insertOrUpdate(Product product) {
        SessionObject sessionObject = getSesionTransactionObject();
        sessionObject.getSession().saveOrUpdate(product);
        sessionObject.commitTransactionAndCloseSession();
        return product;
    }
}
