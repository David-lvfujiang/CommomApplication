package com.nnmzkj.common.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import com.nnmzkj.common.R;
/**
 * Date : 2020/9/8
 * Author : Davaid.lvfujiang
 * Desc : 自定义加载等待loading
 */
public class LoadProgressDialog extends Dialog {
    private String message;
    private boolean canCancel;
    private TextView textView;

    public LoadProgressDialog(Context context, String message) {
        this(context, message, false);
    }

    public LoadProgressDialog(Context context, String message, boolean canCancel) {
        super(context);
        this.message = message;
        this.canCancel = canCancel;
    }

    public void setMessage(String message) {
        this.message = message;
        handler.sendEmptyMessage(0);
    }

    @SuppressLint("HandlerLeak")
    private
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                textView.setText(message);
            }
        }
    };

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loadprogress);
        textView = findViewById(R.id.tv_message);
        //        setCancelable(canCancel);
        setCanceledOnTouchOutside(canCancel);
        textView.setText(message);
        getWindow().setDimAmount(0f);
    }
}
