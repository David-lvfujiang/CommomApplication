package com.nnmzkj.common.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.multidex.MultiDex;
import com.nnmzkj.common.BuildConfig;
import com.nnmzkj.common.manager.AppManager;
import com.nnmzkj.common.manager.CrashHandlerManage;
import com.nnmzkj.common.utils.UserInfoUtils;

/**
 * @ClassName: BaseApplication
 * @Description: 基础Application所有需要模块化开发的module都需要继承自BaseApplication
 * @Author: PZJ
 * @CreateDate: 2019/9/16
 */
public  class BaseApplication extends Application {

    // 全局唯一的context
    private static BaseApplication application;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        application = this;
        // MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = BaseApplication.getApplication().getSharedPreferences(UserInfoUtils.PREFERENCE_TOKEN, Context.MODE_PRIVATE);
        initCrashManage();
    }

    /**
     * 初始化崩溃管理器
     */
    private void initCrashManage() {
        if (!BuildConfig.DEBUG) {
            CrashHandlerManage.getInstance().init(getApplicationContext());
        }
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        AppManager.getAppManager().finishAllActivity();
    }

    /**
     * 获取全局唯一上下文
     *
     * @return BaseApplication
     */
    public static BaseApplication getApplication() {
        return application;
    }



}