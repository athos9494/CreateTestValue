package com.mystic.CreateTestValue.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * @author zhangzhi
 * @desc 加密工具类
 * @date 2025/4/24
 */
public class DecodeUtils {
    public static void main(String[] args) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword("123"); // 设置加密密码
        encryptor.setAlgorithm("PBEWithMD5AndDES"); // 设置加密算法

        String originalValue = "zzzs12138";
        String encryptedValue = encryptor.encrypt(originalValue); // 加密
        System.out.println("Encrypted Value: " + encryptedValue);

        String decryptedValue = encryptor.decrypt(encryptedValue); // 解密
        System.out.println("Decrypted Value: " + decryptedValue);
    }
}
