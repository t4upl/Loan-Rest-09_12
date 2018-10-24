package com.journaldev.entity;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SettingTypePK {
    int productTypeId;
    int settingTypeId;
}
