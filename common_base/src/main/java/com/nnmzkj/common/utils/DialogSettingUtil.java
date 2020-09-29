package com.nnmzkj.common.utils;

import android.graphics.Color;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.util.TextInfo;

/**
 * Date : 2020/6/4
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class DialogSettingUtil {
    public static void setting() {
        TextInfo textInfo = new TextInfo();
        textInfo.setFontColor(Color.rgb(255, 0, 0));
        DialogSettings.style = (DialogSettings.STYLE.STYLE_IOS);      //全局主题风格，提供三种可选风格，STYLE_MATERIAL, STYLE_KONGZUE, STYLE_IOS
        DialogSettings.theme = (DialogSettings.THEME.LIGHT);      //全局明暗风格，提供两种可选主题，LIGHT, DARK
        DialogSettings.titleTextInfo = (textInfo);          //全局标题文字样式
        DialogSettings.contentTextInfo = (textInfo);        //全局正文文字样式
        DialogSettings.buttonTextInfo = (textInfo);         //全局默认按钮文字样式
        DialogSettings.buttonPositiveTextInfo = (textInfo); //全局焦点按钮文字样式（一般指确定按钮）
        DialogSettings.cancelable = (true);              //全局对话框默认是否可以点击外围遮罩区域或返回键关闭，此开关不影响提示框（TipDialog）以及等待框（TipDialog）
    }
}
