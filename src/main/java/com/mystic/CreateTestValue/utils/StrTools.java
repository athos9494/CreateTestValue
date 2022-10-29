package com.mystic.CreateTestValue.utils;

/**
 * @author mystic
 * @date 2022/6/10 00:34
 */
public class StrTools {
    public static void chkStr(String arg, String argName){
        if (isNullOrEmpty(arg)){
            throw new NullPointerException(argName + "不能为空!");
        }
    }

    private static boolean isNullOrEmpty(String value){
        return value == null || "".equals(value);
    }
}
