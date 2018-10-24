package com.journaldev.testDependencies;

import com.journaldev.dao.*;
import com.journaldev.entity.ProductTypeSetting;
import com.journaldev.factory.EntityFactory;
import com.journaldev.other.DecisionSystem;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class OtherTestDependencies {

    @Autowired
    EntityFactory entityFactory;

    @Autowired
    DecisionSystem decisionSystem;

    @Autowired
    ProductTypeSettingDAO productTypeSettingDAO;

    @Autowired
    ProductDAO productDAO;
}
