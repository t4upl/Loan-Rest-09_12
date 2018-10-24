package com.journaldev.manager;

import com.journaldev.dao.ProductSettingDAO;
import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.SettingType;
import com.journaldev.util.DateTimeUtil;
import com.journaldev.util.FilterUtil;
import com.journaldev.util.SettingTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class ProductSettingManagerImpl implements ProductSettingManager {

    @Autowired
    ProductSettingDAO productSettingDAO;

    @Override
    public void insert(List<ProductSetting> productSettings) {
        productSettings.forEach(productSetting -> productSettingDAO.insert(productSetting));
    }

    @Override
    public void extendLoan(int productId) {
        List<ProductSetting> productSettings = productSettingDAO.findByProductId(productId);

        ProductSetting dueDateProductSetting = FilterUtil.findProductSettingByValue(
                productSettings, SettingTypeUtil.dueDate);
        LocalDateTime dueDate = DateTimeUtil.getLocalDateTime(dueDateProductSetting.getValue());

        int extenstion = Integer.parseInt(FilterUtil.findProductSettingByValue(
                productSettings, SettingTypeUtil.extensionTerm).getValue());

        LocalDateTime localDateTime = DateTimeUtil.addDaysToToLocalDateTime(dueDate, extenstion);
        dueDateProductSetting.setValue(DateTimeUtil.localDateTimeToString(localDateTime));
        productSettingDAO.update(dueDateProductSetting);
    }
}
