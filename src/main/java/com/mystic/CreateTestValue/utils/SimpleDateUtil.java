package com.mystic.CreateTestValue.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author mystic
 * @date 2022/8/27 21:09
 */
public class SimpleDateUtil {
    public static String getCurrentDate(){
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    public static String getCurrentTime(){
        return new SimpleDateFormat("HHmmss").format(new Date());
    }
}
