package com.aha.dishordersystem.util;

import java.math.BigDecimal;

public class MathUtils {

    /**
     * 将 double 类型保留两位小数并转换成 String
     * @param number
     * @return
     */
    public static String doubleKeepTwoToString(double number) {
        BigDecimal decimal = new BigDecimal(number);
        return String.valueOf(decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 将 string 类型数字保留两位小数并转换成 String
     * @param number
     * @return
     */
    public static String doubleKeepTwoToString(String number) {
        BigDecimal decimal = new BigDecimal(number);
        return String.valueOf(decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

}
