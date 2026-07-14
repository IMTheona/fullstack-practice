package com.theona.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

    /**
     * MD5加密（返回32位小写）
     */
    public static String encrypt(String plainText) {
        if (plainText == null || plainText.isEmpty()) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(plainText.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5加密（返回32位大写）
     */
    public static String encryptToUpper(String plainText) {
        String result = encrypt(plainText);
        return result == null ? null : result.toUpperCase();
    }

    /**
     * 校验密码是否匹配
     */
    public static boolean verify(String plainText, String md5Hash) {
        if (plainText == null || md5Hash == null) {
            return false;
        }
        String encrypted = encrypt(plainText);
        return encrypted != null && encrypted.equals(md5Hash);
    }

    /**
     * 加盐MD5加密（更安全）
     */
    public static String encryptWithSalt(String plainText, String salt) {
        if (plainText == null || salt == null) {
            return null;
        }
        return encrypt(plainText + salt);
    }

    /**
     * 加盐校验
     */
    public static boolean verifyWithSalt(String plainText, String salt, String md5Hash) {
        if (plainText == null || salt == null || md5Hash == null) {
            return false;
        }
        String encrypted = encryptWithSalt(plainText, salt);
        return encrypted != null && encrypted.equals(md5Hash);
    }

    public static void main(String[] args) {
        // 测试
        String pwd = "123456";
        System.out.println("原始密码: " + pwd);
        System.out.println("MD5加密: " + encrypt(pwd));
        System.out.println("MD5大写: " + encryptToUpper(pwd));
        System.out.println("加盐加密: " + encryptWithSalt(pwd, "abc"));
        System.out.println("密码校验: " + verify(pwd, encrypt(pwd)));
    }
}
