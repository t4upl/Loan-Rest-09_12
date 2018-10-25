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
    public List<ProductTypeSetting> getProductTypeSettingsByProductTypeId(int productTypeId) {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession()
                                         .createCriteria(entityClass)
                                         .add(Restrictions.eq("productTypeId", productTypeId))
                                         .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<ProductTypeSetting> productTypeSettings = criteria.list();
        sessionObject.commitTransactionAndCloseSession();
        return productTypeSettings;
    }
}
