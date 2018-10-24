package com.journaldev.dao;

import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.entity.SettingType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class SettingTypeDAOImpl extends AbstractDAO implements SettingTypeDAO {
    public SettingTypeDAOImpl() {
        super(SettingType.class);
    }

    @Override
    @Transactional
    public SettingType insert(SettingType settingType) {
        SessionObject sessionObject = getSesionTransactionObject();
        sessionObject.getSession().save(settingType);
        sessionObject.commitTransactionAndCloseSession();
        return settingType;
    }
}
