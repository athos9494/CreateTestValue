package com.mystic.CreateTestValue.utils;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Calendar;

/**
 * @author mystic
 * @date 2022/1/15 19:42
 */
@Component
public class FileUtil {
    public static String fileToStr(String filePath){
        long start = Calendar.getInstance().getTimeInMillis();
        File file = new File(filePath);
        try (FileInputStream in = new FileInputStream(file)){
            byte[] bytes = new byte[in.available()];
            System.out.println("读取文件耗时: " + (Calendar.getInstance().getTimeInMillis() - start) + "ms");
            return new String(bytes);
        }catch (Exception e){
            return "null";
        }
    }

    public static byte[] fileToBytes(String filePath){
        long start = Calendar.getInstance().getTimeInMillis();
        byte[] bytes = new byte[0];
        try (FileInputStream in = new FileInputStream(filePath)){
            bytes = new byte[in.available()];
            System.out.println("读取文件耗时: " + (Calendar.getInstance().getTimeInMillis() - start) + "ms");
            return bytes;
        }catch (Exception e){
            e.printStackTrace();
        }
        return bytes;
    }

    public static void byteToFile(byte[] bytes, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath); BufferedOutputStream outputStream = new BufferedOutputStream(fos)) {
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
