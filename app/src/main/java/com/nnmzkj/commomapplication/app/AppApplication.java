package com.nnmzkj.commomapplication.app;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import com.nnmzkj.common.base.BaseApplication;

/**
 * Date : 2020/6/2
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        BGASwipeBackHelper.init(this, null);

    }
}
