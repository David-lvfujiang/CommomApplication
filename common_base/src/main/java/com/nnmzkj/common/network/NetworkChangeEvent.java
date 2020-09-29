package com.nnmzkj.common.network;

/**
 * Date : 2020/9/8
 * Author : Davaid.lvfujiang
 * Desc :网络状态改变事件
 */

public class NetworkChangeEvent {
    private boolean isConnect;

    public NetworkChangeEvent(boolean isConnect) {
        this.isConnect = isConnect;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
