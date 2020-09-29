package com.nnmzkj.common.utils;

import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.kongzue.dialog.interfaces.OnBackClickListener;
import com.kongzue.dialog.interfaces.OnDialogButtonClickListener;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.util.DialogSettings;
import com.kongzue.dialog.v3.BottomMenu;
import com.kongzue.dialog.v3.CustomDialog;
import com.kongzue.dialog.v3.MessageDialog;
import com.kongzue.dialog.v3.ShareDialog;
import com.kongzue.dialog.v3.TipDialog;
import com.kongzue.dialog.v3.WaitDialog;
import java.util.ArrayList;
import java.util.List;

/**
 * Date : 2020/6/4
 * Author : Davaid.lvfujiang
 * Desc : 对话框
 */
public class DialogUtil {
    /**
     * 成功
     *
     * @param context 上下文
     * @param content 内容
     */
    public static void successDiglog(AppCompatActivity context, String content) {
        TipDialog.show(context, content, TipDialog.TYPE.SUCCESS);
    }

    /**
     * 错误
     *
     * @param context 上下文
     * @param content 内容
     */
    public static void errorDiglog(AppCompatActivity context, String content) {
        TipDialog.show(context, content, TipDialog.TYPE.ERROR);
    }

    /**
     * 警告
     *
     * @param context 上下文
     * @param content 内容
     */
    public static void warningDiglog(AppCompatActivity context, String content) {
        TipDialog.show(context, content, TipDialog.TYPE.WARNING);
    }

    /**
     * 等待
     * @param context 上下文
     * @param content 内容
     */
    public static void waitDialog(AppCompatActivity context, String content) {
        WaitDialog.show(context, content).setOnBackClickListener(new OnBackClickListener() {
            @Override
            public boolean onBackClick() {
                return true;
            }
        });
    }

    /**
     * 底部菜单对话框
     *
     * @param context 上下文
     * @param list 菜单
     * @param litener 菜单监听
     */
    public static void buttomLayoutDialog(AppCompatActivity context, ArrayList<String> list, OnMenuItemClickListener litener) {
        DialogSettings.style = DialogSettings.STYLE.STYLE_IOS;
        BaseAdapter baseAdapter = new ArrayAdapter(context, com.kongzue.dialog.R.layout.item_bottom_menu_kongzue, list);
        BottomMenu.show(context, baseAdapter, litener);
    }

    /**
     * 确定对话框
     *
     * @param context 上下文
     * @param title 标题
     * @param Content 内容
     * @param listener 监听回调
     */
    public static void selectMessage(AppCompatActivity context, String title, String Content, OnDialogButtonClickListener listener) {
        MessageDialog.show(context, title, Content, "", "取消")
                .setOkButton("确定", listener)
                .setButtonOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 确定对话框
     *
     * @param context 上下文
     * @param title 标题
     * @param Content 内容
     * @param okButton 确定按钮
     * @param cancelButton 取消按钮
     * @param listener 监听回调
     */
    public static void selectMessageDiaglog(AppCompatActivity context, String title, String Content, String okButton, String cancelButton,
            OnDialogButtonClickListener listener) {
        MessageDialog.show(context, title, Content, "", cancelButton)
                .setOkButton(okButton, listener)
                .setButtonOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 自定义布局对话框
     *
     * @param context 上下文
     * @param layoutResId 布局
     * @param onBindView 回调监听
     */
    public static void customDialog(AppCompatActivity context, int layoutResId, CustomDialog.OnBindView onBindView) {
        CustomDialog.build(context, layoutResId, onBindView).setAlign(CustomDialog.ALIGN.DEFAULT).setCancelable(false).show();
    }

    /**
     * 分享对话框
     *
     * @param context 上下文
     * @param itemList 内容
     * @param listener 监听
     */
    public static void shareDialog(AppCompatActivity context, List<ShareDialog.Item> itemList, ShareDialog.OnItemClickListener listener) {
        ShareDialog.show(context, itemList, listener);
    }
}
