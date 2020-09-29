package com.nnmzkj.common.http;

import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

import static com.nnmzkj.common.http.Constant.Code.FAILURE;
import static com.nnmzkj.common.http.Constant.Code.NET_ERROR;
import static com.nnmzkj.common.http.Constant.Code.NOT_LOGGED;
import static com.nnmzkj.common.http.Constant.Code.PARSING_ERROR;
import static com.nnmzkj.common.http.Constant.Code.TOKEN_INVALID;

/**
 * 统一异常处理类
 */
public class ExceptionHandler {
    public static ResponeThrowable handleException(Throwable e) {
        String errmsg = "";
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case Constant.Code.UNAUTHORIZED:
                case Constant.Code.NOT_FOUND:
                case Constant.Code.REQUEST_TIMEOUT:
                case Constant.Code.GATEWAY_TIMEOUT:
                case Constant.Code.INTERNAL_SERVER_ERROR:
                case Constant.Code.BAD_GATEWAY:
                case Constant.Code.SERVICE_UNAVAILABLE:
                default:
                    errmsg = "网络错误";
                    break;
            }
            errmsg = errmsg + ":" + httpException.code();
            return handleServerException(NET_ERROR, errmsg);
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            int errCode = exception.getErrcode(); // 服务端返回的错误码：400=失败，403=未登录，402=token失效
            switch (errCode) {
                case FAILURE:
                    errmsg = exception.getErrmsg();
                    break;
                case TOKEN_INVALID:
                    errmsg = "Token失效，请重新登录！";
                    break;
                case NOT_LOGGED:
                    errmsg = "您未登录账号哦！";
                    break;
                default:
                    break;
            }
            return handleServerException(errCode, errmsg);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            errmsg = "解析错误";
            return handleServerException(PARSING_ERROR, errmsg);
        } else if (e instanceof ConnectException) {
            errmsg = "网络连接失败,请稍后重试";
            return handleServerException(NET_ERROR, errmsg);
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            e.printStackTrace();
            errmsg = "证书验证失败";
            Log.d(Constant.Code.TAG, "handleException: " + e.getMessage());
            return handleServerException(NET_ERROR, errmsg);
        } else if (e instanceof ConnectTimeoutException) {
            errmsg = "网络连接超时";
            return handleServerException(NET_ERROR, errmsg);
        } else if (e instanceof java.net.SocketTimeoutException) {
            errmsg = "连接超时";
            return handleServerException(NET_ERROR, errmsg);
        } else if (e instanceof java.net.UnknownHostException) {
            errmsg = "请检查您的网络";
            return handleServerException(NET_ERROR, errmsg);
        } else {
            errmsg = "网络连接异常,请稍后重试";
            return handleServerException(NET_ERROR, errmsg);
        }
    }

    /**
     * 根据业务逻辑处理异常信息
     */
    private static ResponeThrowable handleServerException(int errCode, String message) {
        return new ResponeThrowable(errCode, message);
    }

    public static class ResponeThrowable extends Exception {
        private int code;
        private String message;

        public ResponeThrowable(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
