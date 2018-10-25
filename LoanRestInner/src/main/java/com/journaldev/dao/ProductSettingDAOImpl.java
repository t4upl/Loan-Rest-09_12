package com.journaldev.dao;

import com.journaldev.entity.ProductSetting;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public class ProductSettingDAOImpl extends AbstractDAOImpl<ProductSetting> implements ProductSettingDAO {

    public ProductSettingDAOImpl() {
        super(ProductSetting.class);
    }

    @Override
    public void deleteByProductIdAndSettingTypeId(int productId, int settingTypeId) {
        SessionObject sessionObject = getSesionTransactionObject();
        ProductSetting productSetting = findByProductIdAndSettingTypeId(productId, settingTypeId);
        sessionObject.getSession().delete(productSetting);
        sessionObject.commitTransactionAndCloseSession();
    }

    @Override
    public ProductSetting findByProductIdAndSettingTypeId(int productId, int settingTypeId) {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession()
                .createCriteria(entityClass)
                .add(Restrictions.eq("productId", productId))
                .add(Restrictions.eq("settingTypeId", settingTypeId));
        ProductSetting productSetting = (ProductSetting) criteria.uniqueResult();
        sessionObject.commitTransactionAndCloseSession();
        return productSetting;
    }

    @Override
    public List<ProductSetting> findByProductId(int productId) {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession()
                .createCriteria(entityClass)
                .add(Restrictions.eq("productId", productId));

        List<ProductSetting> productSettings = criteria.list();
        sessionObject.commitTransactionAndCloseSession();
        return productSettings;
    }

    @Override
    @Transactional
    public void update(ProductSetting productSetting) {
        SessionObject sessionObject = getSesionTransactionObject();
        sessionObject.getSession().update(productSetting);
        sessionObject.commitTransactionAndCloseSession();
    }

    @Override
    @Transactional
    public ProductSetting insert(ProductSetting productSetting) {
        SessionObject sessionObject = getSesionTransactionObject();
        sessionObject.getSession().save(productSetting);
        sessionObject.commitTransactionAndCloseSession();
        return productSetting;
    }
}
