package com.journaldev.dao;

import com.journaldev.entity.SettingType;
import org.springframework.transaction.annotation.Transactional;

public class SettingTypeDAOImpl extends AbstractDAOImpl<SettingType> implements SettingTypeDAO {
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
