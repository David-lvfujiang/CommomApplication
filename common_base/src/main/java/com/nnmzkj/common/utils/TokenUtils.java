package com.nnmzkj.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import com.nnmzkj.common.base.BaseApplication;

public class TokenUtils {

    public static String PREFERENCE_TOKEN = "PREFERENCE_TOKEN";
    public static String PREFERENCE_USER = "PREFERENCE_USER";
    public static int HAS_NET = 1;
    public static int NO_NET = 0;
    /**
     * 保存token信息
     *
     * @param token token
     * @return 保存结果
     */
    public static boolean saveToken(String token) {
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        return editor.commit();
    }

    /**
     * 获取保存的token信息
     *
     * @return token
     */
    public static String getToken() {
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        if (sharedPreferences == null) {
            return "";
        }
        String token = sharedPreferences.getString("token", "");
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
        String token = getToken();
        if ((token != null && token.length() > 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 缓存网络状态
     *
     * @param state
     * @return
     */
    public static boolean saveNetState(int state) {
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("netState", state);
        return editor.commit();
    }

    /**
     * 获取网络状态
     *
     * @return
     */
    public static int getNetState() {
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(PREFERENCE_USER, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("netState", -1);
    }

}
