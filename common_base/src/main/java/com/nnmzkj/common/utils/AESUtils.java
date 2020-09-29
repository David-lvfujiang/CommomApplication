package com.nnmzkj.common.utils;

/**
 * Date : 2020/9/24
 * Author : Davaid.lvfujiang
 * Desc :
 */

import android.util.Base64;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.jetbrains.annotations.NotNull;

/**
 * @author : yzq
 * @description: AES工具类
 * @date : 2019/3/18
 * @time : 9:54
 */

public class AESUtils {

    private static String cipherMode = "AES/ECB/PKCS5Padding";//算法/模式/补码方式

    /*   AES秘钥支持128bit/192bit/256bit三种长度的秘钥，一个字节等于8bit，
     *   因此支持生成的字符串的长度应该是 16/24/32
     * */
    public static int keyLength = 24;

    /**
     * @param length 需要生成的字符串长度
     * @return 随机生成的字符串
     */
    public static String getRandomKey(int length) {
        if (length != 16 && length != 24 && length != 32) {
            System.out.println("长度必须为16/24/32");
            return null;
        }

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            stringBuilder.append(str.charAt(number));
        }
        return stringBuilder.toString();

    }


    /**
     * @param data 需要加密的数据
     * @param key  加密使用的key
     * @return 加密后的数据(Base64编码)
     * @throws Exception
     */
    public static String encrypt(String data, @NotNull String key) throws Exception {

        int length = key.length();
        if (length != 16 && length != 24 && length != 32) {
            System.out.println("长度必须为16/24/32");
            return null;
        }

        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance(cipherMode);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));

        return Base64.encodeToString(encrypted,Base64.DEFAULT);
    }


    /**
     * @param data 需要解密的数据
     * @param key  解密用的key
     * @return 解密后的数据
     * @throws Exception
     */
    public static String decrypt(String data, @NotNull String key) throws Exception {
        try {
            int length = key.length();
            if (length != 16 && length != 24 && length != 32) {
                System.out.println("长度必须为16/24/32");
                return null;
            }

            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance(cipherMode);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted = Base64.decode(data,Base64.DEFAULT);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted);
                return new String(original, "utf-8");
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

}
