package com.journaldev.other;

import com.journaldev.util.HibernateUtil;
import com.journaldev.util.SettingTypeUtil;
import com.journaldev.dao.ProductTypeSettingDAO;
import com.journaldev.entity.ProductTypeSetting;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Period;
import java.util.Arrays;
import java.util.List;

public class DecisionSystemImpl implements DecisionSystem {

    @Autowired
    ProductTypeSettingDAO productTypeSettingDAO;

    @Override
    public boolean isLoanGiven(ClientDataWrapper clientDataWrapper) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<ProductTypeSetting> productTypeSettings = productTypeSettingDAO.getProductTypeSettingsByProductId(
                clientDataWrapper.getProductTypeId());
        session.close();

        int minAmount = Integer.parseInt(findValueByKey(productTypeSettings, SettingTypeUtil.minAmount));
        int maxAmount = Integer.parseInt(findValueByKey(productTypeSettings, SettingTypeUtil.maxAmount));
        Period minTerm = Period.parse(findValueByKey(productTypeSettings, SettingTypeUtil.minTerm));
        Period maxTerm = Period.parse(findValueByKey(productTypeSettings, SettingTypeUtil.maxTerm));

        if (clientDataWrapper.getAmount() < minAmount || clientDataWrapper.getAmount() > maxAmount) {
            return false;
        }

//        if (clientDataWrapper.getTerm() < minTerm || clientDataWrapper.getTerm() > maxTerm) {
//            return false;
//        }



        return true;
    }

    private String findValueByKey (List<ProductTypeSetting> productTypeSettings, String key) {
//        ProductTypeSetting productTypeSetting = productTypeSettings
//                .stream()
//                .filter(x -> x.getSettingType().getName().equals(key))
//                .findFirst().orElseThrow(() -> new RuntimeException("Key: " + key
//                        + " does not exist in " + Arrays.toString(productTypeSettings.toArray())));
//
//        return productTypeSetting.getValue();
        return null;
    }
}
