package com.nnmzkj.common.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import com.cxz.wanandroid.ext.ExtKt;
import com.nnmzkj.common.manager.AppManager;
import com.nnmzkj.common.utils.UserInfoUtils;
import org.greenrobot.eventbus.EventBus;

/**
 * @Author: david.lvfujiang
 * @Date: 2019/11/5
 * @Describe: android5.0后网络监听接口
 */

@SuppressLint("NewApi")
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {
    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        boolean hasNetwork = UserInfoUtils.getNetworkStatus();
        boolean isConnected = isNetworkConnected(AppManager.getAppManager().currentActivity());
        if (isConnected) {
            //保存当前网络状态（有网）
            UserInfoUtils.saveNetworkStatus(isConnected);
            if (isConnected != hasNetwork) {
                EventBus.getDefault().post(new NetworkChangeEvent(true));
            }
        } else {
            EventBus.getDefault().post(new NetworkChangeEvent(isConnected));
        }
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        ExtKt.showToast(AppManager.getAppManager().currentActivity(),"网络已断开");
        //保存当前网络状态（断网）
        UserInfoUtils.saveNetworkStatus(false);
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
    }

    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}