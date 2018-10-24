package com.journaldev.manager;

import com.journaldev.factory.EntityFactory;
import com.journaldev.other.LoanTransformer;
import com.journaldev.util.AppUtil;
import com.journaldev.util.SettingTypeUtil;
import com.journaldev.dao.ProductDAO;
import com.journaldev.dao.ProductTypeSettingDAO;
import com.journaldev.entity.Product;
import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.other.ClientDataWrapper;
import com.journaldev.other.DecisionSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProductManagerImpl implements ProductManager {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductTypeSettingDAO productTypeSettingDAO;

    @Autowired
    DecisionSystem decisionSystem;

    @Autowired
    ProductSettingManager productSettingManager;

    @Autowired
    EntityFactory entityFactory;

    @Autowired
    LoanTransformer loanTransformer;


    @Override
    public void takeLoan(ClientDataWrapper clientDataWrapper) {
//        if (decisionSystem.isLoanGiven(clientDataWrapper)) {
//            insertProduct(clientDataWrapper.getProductTypeId(), clientDataWrapper.getCustomerId());
//        }

        insertProduct(clientDataWrapper, clientDataWrapper.getCustomerId());
    }

    @Transactional
    private void insertProduct(ClientDataWrapper clientDataWrapper, int customerId) {
        int productTypeId = clientDataWrapper.getProductTypeId();

        Product product = new Product();
        product.setCustomerId(customerId);
        product.setProductTypeId(productTypeId);

        product = productDAO.insert(product);
        List<ProductTypeSetting> productTypeSettings = productTypeSettingDAO.
                                                           getProductTypeSettingsByProductId(productTypeId);


        List<ProductSetting> productSettings = loanTransformer.transformProductTypeSettingsToProductSettings(
                                                    productTypeSettings, product.getId(), clientDataWrapper);
        productSettingManager.insert(productSettings);
    }

//    private List<ProductSetting> transformProductTypeSettings (List<ProductTypeSetting> productTypeSettings,
//                                                               final int productId,
//                                                               final ClientDataWrapper clientDataWrapper) {
//        return productTypeSettings.stream()
//                                  .map(x-> transformProductTypeSettingsFunctionSingleField(
//                                                                            x, productId, clientDataWrapper))
//                                  .collect(Collectors.toList());
//    }
//
//    private ProductSetting transformProductTypeSettingsFunctionSingleField(ProductTypeSetting productTypeSetting,
//                                                                           int productId,
//                                                                           ClientDataWrapper clientDataWrapper) {
//
//        ProductSetting productSetting = entityFactory.getProductSetting(
//                -1, productId, productTypeSetting.getSettingTypeId(), productTypeSetting.getValue());
//
//        if (productTypeSetting.getProductTypeId() == 1) {
//            productSetting = singleFieldTransforProduct1(productSetting,
//                                                                 productTypeSetting.getSettingType().getName(),
//                                                                 clientDataWrapper);
//        }
//
//        return productSetting;
//    }

//    private ProductSetting singleFieldTransforProduct1(ProductSetting productSetting,
//                                                       String settingTypeName,
//                                                       ClientDataWrapper clientDataWrapper) {
//        switch (settingTypeName){
//            case (SettingTypeUtil.applicationDate):
//                productSetting.setValue(AppUtil.localDateTimeToString(LocalDateTime.now()));
//                break;
//            case (SettingTypeUtil.amount):
//                productSetting.setValue(clientDataWrapper.getAmount().toString());
//                break;
//            case (SettingTypeUtil.dueDate):
//                productSetting.setValue(AppUtil.localDateTimeToString(LocalDateTime.now()));
//                break;
//        }
//
//        return productSetting;
//    }

//    private ProductSetting makeProduceSetting(int productId, int settingTypeId, String value) {
//        ProductSetting productSetting = new ProductSetting();
//        productSetting.setProductId(productId);
//        productSetting.setSettingTypeId(settingTypeId);
//        productSetting.setValue(value);
//        return productSetting;
//    }
}
