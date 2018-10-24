package com.journaldev.dao;

import com.journaldev.entity.ProductTypeSetting;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ProductTypeSettingDAOImpl extends AbstractDAOImpl<ProductTypeSetting> implements ProductTypeSettingDAO {

    public ProductTypeSettingDAOImpl() {
        super(ProductTypeSetting.class);
    }

    @Override
    public ProductTypeSetting findById(int id) {
        SessionObject sessionObject = getSesionTransactionObject();
        ProductTypeSetting productTypeSetting = (ProductTypeSetting) sessionObject.getSession().get(entityClass, id);
        sessionObject.commitTransactionAndCloseSession();
        return productTypeSetting;
    }

    @Override
    public List<ProductTypeSetting> getProductTypeSettingsByProductId(int productTypeId) {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession()
                                         .createCriteria(entityClass)
                                         .add(Restrictions.eq("productTypeId", productTypeId))
                                         .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<ProductTypeSetting> productTypeSettings = criteria.list();
        sessionObject.commitTransactionAndCloseSession();
        return productTypeSettings;
    }

    @Override
    public Long getCount(ProductTypeSetting productTypeSetting) {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession().createCriteria(entityClass)
                                                      .add(Example.create(productTypeSetting))
                                                      .setProjection(Projections.rowCount());
        Long count = processCriteria(criteria);
        sessionObject.commitTransactionAndCloseSession();
        return count;
    }
}
