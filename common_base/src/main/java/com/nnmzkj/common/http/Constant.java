package com.nnmzkj.common.http;

import com.nnmzkj.common.base.BaseApplication;
import java.io.File;

/**
 * @author David-lvfujiang
 * @time 2019/12/26 15:17
 * desc 常量
 */
public interface Constant {
    interface SP {
        String COOKIE = "cookie";
    }

    interface Sevice {
        //url必须以/结尾
        String SEVICE_URL = "http://sanran.mzsystem.cn/";
        String CHAT_URL = "http://chat.sanrangas.com/";
        String WS_URL = "ws://chat.sanrangas.com:7272";//websocket
    }

    interface Code {
        String TAG = "ExceptionHandler";
        int UNAUTHORIZED = 401;
        int NOT_FOUND = 404;
        int REQUEST_TIMEOUT = 408;
        int INTERNAL_SERVER_ERROR = 500;
        int BAD_GATEWAY = 502;
        int SERVICE_UNAVAILABLE = 503;
        int GATEWAY_TIMEOUT = 504;
        int BIZ_TO_LOGIN = 4002;
        int NET_ERROR = 0; //网络错误
        int OTHER_ERROR = 0001;//其他错误

        int FAILURE = 400; // 失败
        int NOT_LOGGED = 403;//未登录
        int TOKEN_INVALID = 402;//token失效
        int NO_DATA = 10002; // 无数据
        int PARSING_ERROR = 10003;//解析错误
    }

    public static class Photo {
        public static final String DirectoryPath =
                BaseApplication.getApplication().getCacheDir().getAbsolutePath() + File.separator + "imgs" + File.separator;
    }
}
