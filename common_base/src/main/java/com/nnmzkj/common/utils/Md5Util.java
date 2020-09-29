package com.nnmzkj.common.utils;

import java.security.MessageDigest;
import java.util.Random;

/**
 * Date : 2020/9/23
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class Md5Util {
    /**
     * HexUtil类实现Hex(16进制字符串和)和字节数组的互转
     */
    @SuppressWarnings("unused")
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return new String(new HexUtil().encode(digest));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return "";
        }
    }

    /**
     * 加盐MD5加密
     */
    public static String getSaltMD5(String password) {
        // 生成一个16位的随机数
        Random random = new Random();
        StringBuilder sBuilder = new StringBuilder(16);
        sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sBuilder.append("0");
            }
        }
        //生成最终的加密盐
        String Salt = sBuilder.toString();
        //将盐值和密码相加后再加密,得到32位的16进制数据
        password = md5Hex(password.trim() + Salt);
        //再由盐值+加密后的密码重新组合生成48位的16进制,组合规则是每三位数的中间值代表我们的盐值
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            //在结果中的每三位,用中间位保存salt值
            cs[i] = password.charAt(i / 3 * 2);
            char c = Salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }

    /**
     * 验证加盐后是否和原文一致
     *
     * @param password 明文密码
     * @param md5str 数据库取的密码值（加密的密码+盐值组合成的一个48位16进制数）
     * @return 密码是否一致
     */
    public static boolean getSaltverifyMD5(String password, String md5str) {
        //加密的密码
        char[] cs1 = new char[32];
        //盐值
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5str.charAt(i + 2);
            cs2[i / 3] = md5str.charAt(i + 1);
        }
        String Salt = new String(cs2);
        return md5Hex(password + Salt).equals(String.valueOf(cs1));
    }

    public static class HexUtil {
        /**
         * 字节流转成十六进制表示
         */
        public static String encode(byte[] src) {
            String strHex = "";
            StringBuilder sb = new StringBuilder("");
            for (int n = 0; n < src.length; n++) {
                strHex = Integer.toHexString(src[n] & 0xFF);
                // 每个字节由两个字符表示，位数不够，高位补0
                sb.append((strHex.length() == 1) ? "0" + strHex : strHex);
            }
            return sb.toString().trim();
        }

        /**
         * 字符串转成字节流
         */
        public static byte[] decode(String src) {
            int m = 0, n = 0;
            int byteLen = src.length() / 2; // 每两个字符描述一个字节
            byte[] ret = new byte[byteLen];
            for (int i = 0; i < byteLen; i++) {
                m = i * 2 + 1;
                n = m + 1;
                int intVal = Integer.decode("0x" + src.substring(i * 2, m) +
                        src.substring(m, n));
                ret[i] = Byte.valueOf((byte) intVal);
            }
            return ret;
        }
    }
}
