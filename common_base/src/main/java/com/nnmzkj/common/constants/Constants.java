package com.nnmzkj.common.constants;

/**
 * @ClassName: Constants
 * @Description: 常量存放
 * @Author: PZJ
 * @CreateDate: 2019/9/16
 */
public interface Constants {

    /**
     * 默认日期格式
     */
    String DATE_FORMAT_SLASH = "yyyy/MM/dd";
    String DATE_FORMAT_LINE = "yyyy-MM-dd";
    String DATE_FORMAT_DEFAULT = DATE_FORMAT_SLASH + " HH:mm:ss";
    /** 登录界面activity的全路径 */
    String LOGIN_ACTIVITY = "com.nnmzkj.sanrancustomer.activity.LoginActivity";
    /** 登录成功状态码 */
    int LOGIN_SUCCESS_CODE = 200;
    /** 登录失败状态码 */
    int LOGIN_DEFAIL_CODE = 400;

    public interface HttpCode {
        /** 请求成功 */
        int SUCCESS = 200;
        /** 请求失败 */
        int DEFAIL = 400;
        /** 未登录 */
        int NOT_LOGGED = 403;
        /** token失效 */
        int TOKEN_INVALID = 402;
        /** 网络错误 */
        int NET_ERROR = 0;
    }
}

