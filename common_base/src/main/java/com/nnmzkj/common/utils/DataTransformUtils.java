package com.nnmzkj.common.utils;

import android.util.Base64;
import androidx.annotation.NonNull;
import com.nnmzkj.common.http.ApiException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

public class DataTransformUtils {
    //编码key
    public static String ENCRYPT_KEY = "zmR75OEgBuw7iimSfeOYYIDWMVSfXet0";
    //解码key
    public static String DECRYPT_KEY = "zmR75OEgBuw7iimSfeOYYIDWMVSfXet0";

    /**
     * 获取签名
     * @param params 被签名的参数
     * @param signKey 签名key
     * @return 签名
     */
   public static String getParamsSign(SortedMap<String, Object> params, String signKey){
       StringBuilder sb = new StringBuilder();
       Set es = params.entrySet();//所有参与传参的参数按照accsii排序（升序）
       Iterator it = es.iterator();
       while(it.hasNext()) {
           Map.Entry entry = (Map.Entry)it.next();
           String k = (String)entry.getKey();
           Object v = entry.getValue();
           if(null != v && !"".equals(v)
                   && !"sign".equals(k) && !"key".equals(k)) {
               sb.append(k + "=" + v + "&");
           }
       }

       sb.append("key=" + signKey);

       return md5(sb.toString()).toUpperCase();
   }

    /**
     * 生成32位md5码
     *
     * @param key 要加密的字符串
     * @return 已加密的字符串
     */
    public static String md5(String key) {
        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取随机字符串
     * @param length 要获取的随机字符串长度
     * @return 随机字符串
     */
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 内容解密
     * @param dataStr 要解密的内容
     * @param dataKey 数据key
     * @param expireTime 服务器返回来的过期时间戳
     * @return 解密后的内容
     */
    public static String decryptData(String dataStr, String dataKey, String expireTime) {

        //把dataKey 拼接过期时间，用md5加密，然后再用md5再加密一遍。
        String salt = md5(md5(dataKey+expireTime));
        //把加密内容用base64解码，解码后编码方式是ISO_8859_1，ISO_8859_1兼容ASCII
        byte[] decodeBytes = Base64.decode(dataStr, Base64.NO_WRAP);
        String decodeStr = new String(decodeBytes, StandardCharsets.ISO_8859_1);

        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<decodeStr.length(); i++){
            if (index == salt.length())
                index = 0;
           sb.append(salt.charAt(index));
           index++;
        }

        StringBuilder sb2 = new StringBuilder();
        for (int i=0; i<decodeStr.length(); i++){
            int charValue1 = decodeStr.charAt(i);
            int charValue2 = sb.charAt(i);

            int resultCharValue;
            if (charValue1<charValue2){
                resultCharValue = charValue1+256 - charValue2;
            }else {
                resultCharValue = charValue1 - charValue2;
            }
            sb2.append((char) resultCharValue);
        }

        try {
            byte[] resultBytes = Base64.decode(sb2.toString(),Base64.NO_WRAP);
            return new String(resultBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(0, "数据解码错误！");
        }
    }

    /**
     * 内容解密
     * @param dataStr 要解密的内容
     * @param dataKey 数据key elinDvT7GLlwchvC
     * @return 解密后的内容
     */
    public static String decryptData(String dataStr, String dataKey) {

        //把dataKey 用md5加密。
        String salt = md5(dataKey);
        //把加密内容用base64解码，解码后编码方式是ISO_8859_1，ISO_8859_1兼容ASCII
        byte[] decodeBytes = Base64.decode(dataStr, Base64.NO_WRAP);
        String decodeStr = new String(decodeBytes, StandardCharsets.ISO_8859_1);

        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<decodeStr.length(); i++){
            if (index == salt.length())
                index = 0;
            sb.append(salt.charAt(index));
            index++;
        }

        StringBuilder sb2 = new StringBuilder();
        for (int i=0; i<decodeStr.length(); i++){
            int charValue1 = decodeStr.charAt(i);
            int charValue2 = sb.charAt(i);

            int resultCharValue;
            if (charValue1<charValue2){
                resultCharValue = charValue1+256 - charValue2;
            }else {
                resultCharValue = charValue1 - charValue2;
            }
            sb2.append((char) resultCharValue);
        }

        try {
            byte[] resultBytes = Base64.decode(sb2.toString(),Base64.NO_WRAP);
            return new String(resultBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(0, "数据解码错误！");
        }
    }

    /**
     * 修改密码时要传的密码加密
     * @param dataStr 密码
     * @param timestamp 传参时的时间戳
     * @return 加密后的密码
     */
    public static String encryptPassword(@NonNull String dataStr, @NonNull String timestamp) {
        dataStr = Base64.encodeToString(dataStr.getBytes(), Base64.NO_WRAP);
        String salt = md5("APPSRUSER"+timestamp);

        int index = 0;
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<dataStr.length(); i++){
            if (index == salt.length())
                index = 0;
            sb.append(salt.charAt(index));
            index++;
        }

        StringBuilder sb2 = new StringBuilder();
        for (int i=0; i<dataStr.length(); i++){
            int charValue1 = dataStr.charAt(i);
            int charValue2 = sb.charAt(i);
            int resultChar = charValue1+charValue2%256;
            sb2.append((char) resultChar);
        }
        //注意编码问题
        return Base64.encodeToString(sb2.toString().getBytes(StandardCharsets.ISO_8859_1), Base64.NO_WRAP);
    }

}
