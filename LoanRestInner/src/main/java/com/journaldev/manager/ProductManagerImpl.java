package com.journaldev.manager;

import com.journaldev.factory.EntityFactory;
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
    DecisionSystem decisionSystem;

    @Autowired
    LoanTransformer loanTransformer;

    @Autowired
    EntityFactory entityFactory;

    //--------------------------
    // Manager

    @Autowired
    ProductSettingManager productSettingManager;

    //--------------------------
    // DAO

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductTypeSettingDAO productTypeSettingDAO;


    @Override
    public Product applyForLoan(ClientDataWrapper clientDataWrapper) {
        if (decisionSystem.isLoanGiven(clientDataWrapper)) {
            return insertProduct(clientDataWrapper);
        }

        return null;
    }

    @Transactional
    private Product insertProduct(ClientDataWrapper clientDataWrapper) {
        int productTypeId = clientDataWrapper.getProductTypeId();
        int customerId = clientDataWrapper.getCustomerId();

        Product product = entityFactory.getProduct(-1, productTypeId, customerId);

        product = productDAO.insert(product);
        List<ProductTypeSetting> productTypeSettings = productTypeSettingDAO.
                getProductTypeSettingsByProductTypeId(productTypeId);

        List<ProductSetting> productSettings = loanTransformer.transformProductTypeSettingsToProductSettings(
                productTypeSettings, product.getId(), clientDataWrapper);
        productSettingManager.insert(productSettings);
        return product;
    }
}
