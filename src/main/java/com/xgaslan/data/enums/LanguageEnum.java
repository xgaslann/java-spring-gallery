package com.xgaslan.data.enums;

public enum LanguageEnum {
    TURKISH("TR", "Türkçe"),
    ENGLISH("EN", "English");

    private final String code;
    private final String desc;

    LanguageEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
