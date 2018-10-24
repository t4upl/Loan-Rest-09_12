package com.journaldev.manager;

import com.journaldev.other.LoanTransformer;
import com.journaldev.dao.ProductDAO;
import com.journaldev.dao.ProductTypeSettingDAO;
import com.journaldev.entity.Product;
import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.other.ClientDataWrapper;
import com.journaldev.other.DecisionSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    LoanTransformer loanTransformer;


    @Override
    public Product applyForLoan(ClientDataWrapper clientDataWrapper) {
//        if (decisionSystem.isLoanGiven(clientDataWrapper)) {
//            insertProduct(clientDataWrapper.getProductTypeId(), clientDataWrapper.getCustomerId());
//        }

        return insertProduct(clientDataWrapper, clientDataWrapper.getCustomerId());
    }

    @Transactional
    private Product insertProduct(ClientDataWrapper clientDataWrapper, int customerId) {
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
        return product;
    }
}
