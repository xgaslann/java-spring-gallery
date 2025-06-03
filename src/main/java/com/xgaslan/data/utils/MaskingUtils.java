package com.xgaslan.data.utils;

public class MaskingUtils {
    private MaskingUtils() {}

    public static String maskNationalNumber(String value){
        if (value == null || value.length() < 4)
            return "****";

        var maskLength = value.length() - 4;
        return "*".repeat(maskLength) + value.substring(maskLength);
    }

    public static String maskCardNumber(String value) {
        if (value == null || value.length() < 4)
            return "****";

        return "**** **** **** " + value.substring(value.length() - 4);
    }

    public static String maskIban(String value) {
        if (value == null || value.length() < 4)
            return "****";

        var maskLength = value.length() - 4;
        return "*".repeat(maskLength) + value.substring(maskLength);
    }
}
