package com.nnmzkj.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.nnmzkj.common.base.BaseApplication;
import com.nnmzkj.common.bean.Token;

public class UserInfoUtils {

    public static String PREFERENCE_TOKEN = "PREFERENCE_TOKEN";
    public static String USER_ACCOUNT = "USER_ACCOUNT";
    public static String USER_PASSWORD = "USER_PASSWORD";


    /**
     * 保存token信息
     *
     * @param token token
     * @return 保存结果
     */
    public static boolean saveToken(Token token) {
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token.getToken());
        editor.putInt("uid", token.getUid());
        return editor.commit();
    }

    /**
     * 获取保存的token信息
     *
     * @return token
     */
    public static Token getToken() {
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        Token token = new Token();
        token.setToken(sharedPreferences.getString("token", ""));
        token.setUid(sharedPreferences.getInt("uid", -1));
        return token;
    }

    /**
     * 清除登录信息
     */
    public static void clearAllLoginInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //BaseApplication.getApplication().deleteSharedPreferences(PREFERENCE_USER);
            BaseApplication.getApplication().deleteSharedPreferences(PREFERENCE_TOKEN);
        } else {
            //            SharedPreferences usersp = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
            ////            SharedPreferences.Editor userEditor = usersp.edit();
            ////            userEditor.clear();
            ////            userEditor.apply();

            SharedPreferences tokensp = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
            SharedPreferences.Editor tokenEditor = tokensp.edit();
            tokenEditor.clear();
            tokenEditor.apply();
        }
    }

    /**
     * 检查登录状态是否可用
     *
     * @return 是否可用
     */
    public static boolean checkLoginStatusAvailable() {
        Token token = getToken();
        if ((token.getToken() != null && token.getToken().length() > 0)) {
            return true;
        } else {
            return false;
        }
    }



    /**
     * 保存网络状态
     *
     * @param b
     * @return 保存结果
     */
    public static boolean saveNetworkStatus(boolean b) {
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("has_network", b);
        return editor.commit();
    }

    /**
     * 获取网络状态
     *
     */
    public static boolean getNetworkStatus() {
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
      return   sharedPreferences.getBoolean("has_network",true);
    }



}
