package com.journaldev.other;

import com.journaldev.adapter.ProductTypeSettingAdapter;
import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.util.AppUtil;
import com.journaldev.util.SettingTypeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.journaldev.util.SettingTypeUtil.applicationDate;

public class LoanTransformerImpl implements LoanTransformer {

    @Autowired
    ProductTypeSettingAdapter productTypeSettingAdapter;

    @Override
    public List<ProductSetting> transformProductTypeSettingsToProductSettings(
                                    List<ProductTypeSetting> productTypeSettings, int productId,
                                    ClientDataWrapper clientDataWrapper) {

        List<ProductSetting> productSettings = mapProductTypeSettingsToProductSetting(productTypeSettings, productId);

        Map<String, ProductSetting> map =   productSettings
                                                .stream()
                                                .collect(Collectors.toMap(
                                                    producSetting -> producSetting.getSettingType().getName(),
                                                    productSetting -> productSetting));

        transformProductSettingsMap(map, clientDataWrapper);

        return new ArrayList<>(map.values());
    }

    private List<ProductSetting> mapProductTypeSettingsToProductSetting (List<ProductTypeSetting> productTypeSettings,
                                                                         final int productId) {
        return productTypeSettings.stream()
                .map(productTypeSetting -> productTypeSettingAdapter.getProductSetting(productTypeSetting, productId))
                .collect(Collectors.toList());
    }

    private void transformProductSettingsMap (Map<String, ProductSetting> productSettingMap,
                                              ClientDataWrapper clientDataWrapper) {

        transformProductSettingMapEntry(SettingTypeUtil.applicationDate, productSettingMap, null);
        transformProductSettingMapEntry(SettingTypeUtil.amount, productSettingMap, clientDataWrapper);
        transformProductSettingMapEntry(SettingTypeUtil.dueDate, productSettingMap, clientDataWrapper);
    }

    private void transformProductSettingMapEntry (String settingTypeName, Map<String, ProductSetting> productSettingMap,
                                                  ClientDataWrapper clientDataWrapper) {
        ProductSetting productSetting = productSettingMap.get(settingTypeName);

        switch (settingTypeName) {
            case (SettingTypeUtil.applicationDate):
                productSetting.setValue(AppUtil.localDateTimeToString(LocalDateTime.now()));
                break;
            case (SettingTypeUtil.amount):
                productSetting.setValue(clientDataWrapper.getAmount().toString());
                break;
            case (SettingTypeUtil.dueDate):
                int numberOfDays = clientDataWrapper.getTerm();
                productSetting.setValue(AppUtil.localDateTimeToString(
                        AppUtil.addDaysToToLocalDateTime(LocalDateTime.now(), numberOfDays)));
                break;
        }

        productSettingMap.put(settingTypeName, productSetting);
    }



}
