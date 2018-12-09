package com.journaldev.other;

import com.journaldev.entity.ProductSetting;
import com.journaldev.entity.ProductTypeSetting;

import java.util.List;

public interface LoanTransformer {
    List<ProductSetting> transformProductTypeSettingsToProductSettings (List<ProductTypeSetting> productTypeSettings,
                                                                        final int productId,
                                                                        final ClientDataWrapper clientDataWrapper);
}
