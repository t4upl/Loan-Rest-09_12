package com.journaldev.dao;

import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductSettingPK;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public class ProductSettingDAOImpl extends AbstractDAO implements ProductSettingDAO {

    public ProductSettingDAOImpl() {
        super(ProductSetting.class);
    }


    @Override
    @Transactional
    public void delete(ProductSettingPK productSettingPK) {
        SessionObject sessionObject = getSesionTransactionObject();
        Optional<ProductSetting> productSettingOptional = findById(productSettingPK);
        productSettingOptional.ifPresent(productSetting -> sessionObject.getSession().delete(productSetting));
        sessionObject.commitTransactionAndCloseSession();
    }

    @Override
    public Optional<ProductSetting> findById(ProductSettingPK productSettingPK) {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession()
                                   .createCriteria(entityClass)
                                   .add(Restrictions.eq("productId", productSettingPK.getProductId()))
                                   .add(Restrictions.eq("settingTypeId",
                                                                        productSettingPK.getSettingTypeId()));
        ProductSetting productSetting = (ProductSetting) criteria.uniqueResult();
        sessionObject.commitTransactionAndCloseSession();
        return Optional.ofNullable(productSetting);
    }

    @Override
    @Transactional
    public ProductSetting insert(ProductSetting productSetting) {
        SessionObject sessionObject = getSesionTransactionObject();
        System.out.println(productSetting);
        sessionObject.getSession().save(productSetting);
        sessionObject.commitTransactionAndCloseSession();
        return productSetting;
    }

    @Override
    public Long getCount() {
        SessionObject sessionObject = getSesionTransactionObject();
        Criteria criteria = sessionObject.getSession().createCriteria(entityClass)
                .setProjection(Projections.rowCount());

        Long rowCount = processCriteria(criteria);
        sessionObject.commitTransactionAndCloseSession();
        return rowCount;
    }
}
