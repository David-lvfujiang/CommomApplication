package com.nnmzkj.commomapplication.util;

import com.nnmzkj.commomapplication.apiservice.ApiService;
import com.nnmzkj.commomapplication.apiservice.UserService;
import com.nnmzkj.common.http.ChatClient;
import com.nnmzkj.common.http.RetrofitClient;

public class ApiServiceUtil {

    public static ApiService apiService() {
        return RetrofitClient.getInstance().create(ApiService.class);
    }

    public static UserService userService() {
        return RetrofitClient.getInstance().create(UserService.class);
    }

    public static UserService userServiceChat() {
        return ChatClient.getInstance().create(UserService.class);
    }
}
