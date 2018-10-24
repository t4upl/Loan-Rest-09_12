package com.journaldev.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProductSettingPK {
    int productId;
    int settingTypeId;
}
