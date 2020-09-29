package com.nnmzkj.common.view;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.nnmzkj.common.R;

/**
 * Date : 2020/9/8
 * Author : Davaid.lvfujiang
 * Desc : 自定义带图标的Toast
 */
public class CustomToast {
    private Toast toast;
    private TextView textView;
    private ImageView imageView;
    private Context context;

    public CustomToast(Context context, int duration) {
        this.context = context;
        toast = new Toast(context);
        toast.setDuration(duration);
    }

    /**
     * 成功弹窗Toast
     * @param content
     */
    public void success(String content) {
        View view = View.inflate(context, R.layout.toast_custom, null);
        textView = view.findViewById(R.id.tv_prompt);
        imageView = view.findViewById(R.id.image);
        textView.setText(content);
        imageView.setImageResource(R.mipmap.success);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 失败弹窗Toast
     * @param content
     */
    public void error(String content) {
        View view = View.inflate(context, R.layout.toast_custom, null);
        textView = view.findViewById(R.id.tv_prompt);
        imageView = view.findViewById(R.id.image);
        textView.setText(content);
        imageView.setImageResource(R.mipmap.error);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 普通弹窗Toast
     * @param content
     */
    public void show(String content) {
        View view = View.inflate(context, R.layout.toast_custom_s, null);
        textView = view.findViewById(R.id.tv_prompt);
        textView.setText(content);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



}
