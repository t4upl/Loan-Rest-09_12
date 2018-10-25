package com.journaldev.other;

import com.journaldev.util.DateTimeUtil;
import com.journaldev.util.FilterUtil;
import com.journaldev.util.SettingTypeUtil;
import com.journaldev.dao.ProductTypeSettingDAO;
import com.journaldev.entity.ProductTypeSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Filter;

public class DecisionSystemImpl implements DecisionSystem {

    @Autowired
    ProductTypeSettingDAO productTypeSettingDAO;

    @Override
    public boolean isLoanGiven(ClientDataWrapper clientDataWrapper) {
        List<ProductTypeSetting> productTypeSettings = productTypeSettingDAO.getProductTypeSettingsByProductTypeId(
                clientDataWrapper.getProductTypeId());

        return new Decision(productTypeSettings, clientDataWrapper)
                    .isAmountInRange()
                    .isTermInRange()
                    .isOutsideOfRejectionHours()
                    .getDecision();
    }

    private static class Decision {
        boolean isLoanGiven;
        List<ProductTypeSetting> productTypeSettings;
        ClientDataWrapper clientDataWrapper;

        public Decision(List<ProductTypeSetting> productTypeSettings, ClientDataWrapper clientDataWrapper) {
            this.isLoanGiven = true;
            this.productTypeSettings = productTypeSettings;
            this.clientDataWrapper = clientDataWrapper;
        }

        public boolean getDecision() {
            return isLoanGiven;
        }

        public Decision isAmountInRange()  {
            int minAmount = Integer.parseInt(findValueByKey(productTypeSettings, SettingTypeUtil.minAmount));
            int maxAmount = Integer.parseInt(findValueByKey(productTypeSettings, SettingTypeUtil.maxAmount));
            if (clientDataWrapper.getAmount() < minAmount || clientDataWrapper.getAmount() > maxAmount) {
                setIsLoanGivenToFalse();
            }
            return this;
        }

        public Decision isTermInRange()  {
            int minTerm = Integer.parseInt(findValueByKey(productTypeSettings, SettingTypeUtil.minTerm));
            int maxTerm = Integer.parseInt(findValueByKey(productTypeSettings, SettingTypeUtil.maxTerm));
            if (clientDataWrapper.getTerm() < minTerm || clientDataWrapper.getTerm() > maxTerm) {
                setIsLoanGivenToFalse();
            }
            return this;
        }

        public Decision isOutsideOfRejectionHours() {
            LocalDateTime applicationDate = clientDataWrapper.getApplicationDate();

            LocalTime minRejectionTime = DateTimeUtil.getLocalTime(findValueByKey(productTypeSettings,
                    SettingTypeUtil.minRejectionHour));
            LocalTime maxRejectionTime = DateTimeUtil.getLocalTime(findValueByKey(productTypeSettings,
                    SettingTypeUtil.maxRejectionHour));

            LocalDateTime minRejectionDate = DateTimeUtil.getLocalDateTimeWithLocalTime(applicationDate,
                    minRejectionTime);
            LocalDateTime maxRejectionDate = DateTimeUtil.getLocalDateTimeWithLocalTime(applicationDate,
                    maxRejectionTime);

            int maxAmount = Integer.parseInt(findValueByKey(productTypeSettings, SettingTypeUtil.maxAmount));

            if (applicationDate.isAfter(minRejectionDate) && applicationDate.isBefore(maxRejectionDate) &&
                    clientDataWrapper.getAmount().equals(maxAmount)) {
                setIsLoanGivenToFalse();
            }

            return this;
        }


        private void setIsLoanGivenToFalse() {
            this.isLoanGiven = false;
        }

        private String findValueByKey (List<ProductTypeSetting> productTypeSettings, String key) {
            return FilterUtil.findProductTypeSettingByValue(productTypeSettings, key).getValue();
        }

 //TODO
//        private String findValueByKey (List<ProductTypeSetting> productTypeSettings, String key) {
//            ProductTypeSetting productTypeSetting = productTypeSettings
//                    .stream()
//                    .filter(x -> x.getSettingType().getName().equals(key))
//                    .findFirst().orElseThrow(() -> new RuntimeException("Key: " + key
//                            + " does not exist in " + Arrays.toString(productTypeSettings.toArray())));
//
//            return productTypeSetting.getValue();
//        }


    }
}
