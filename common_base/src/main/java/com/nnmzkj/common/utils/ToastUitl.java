package com.nnmzkj.common.utils;

import android.content.Context;
import android.widget.Toast;
import com.nnmzkj.common.view.CustomToast;

/**
 * Date : 2020/9/9
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class ToastUitl {
    public static void showToast(Context context, String message) {
        new CustomToast(context, Toast.LENGTH_SHORT).show(message);
    }

    public static void showSuccesToast(Context context, String message) {
        new CustomToast(context, Toast.LENGTH_SHORT).success(message);
    }

    public static void showErrorToast(Context context, String message) {
        new CustomToast(context, Toast.LENGTH_SHORT).error(message);
    }
}
