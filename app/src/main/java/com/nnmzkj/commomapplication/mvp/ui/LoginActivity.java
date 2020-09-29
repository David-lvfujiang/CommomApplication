package com.nnmzkj.commomapplication.mvp.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.nnmzkj.commomapplication.R;
import com.nnmzkj.commomapplication.mvp.contract.MvpTestContract;
import com.nnmzkj.commomapplication.mvp.presenter.MvpTestPresenter;
import com.nnmzkj.common.base.BaseMVPActivity;
import com.nnmzkj.common.bean.Token;
import com.nnmzkj.common.utils.UserInfoUtils;

/**
 * Date : 2020/6/2
 * Author : Davaid.lvfujiang
 * Desc : 登录界面
 */
public class LoginActivity extends BaseMVPActivity<MvpTestPresenter> implements MvpTestContract.View {
    /** Android版本变量 */
    private final int VERSION = Build.VERSION.SDK_INT;
    private final int VERSION_BORDER = 18;
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.button) Button button;
    @BindView(R.id.button2) Button button2;

    @Override protected int getLayoutResId() {
        return R.layout.activity_login2;
    }

    @Override protected void initView() {
        //android调用js代码
        button.setOnClickListener(v -> {
            // 通过Handler发送消息
            webView.post(() -> {
                // 注意调用的JS方法名要对应上
                // 调用javascript的callJS()方法
                if (VERSION < VERSION_BORDER) {
                    webView.loadUrl("javascript:callJS()");
                } else {
                    webView.evaluateJavascript("javascript:callJS()", value -> {
                        //此处为 js 返回的结果
                        Log.e("tag", value);
                    });
                }
            });
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override protected void initData() {
        WebSettings webSettings = webView.getSettings();
        //设置支持js
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //设置自适应屏幕，两者合用
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        // 通过addJavascriptInterface()将Java对象映射到JS对象，AndroidtoJS类对象映射到js的test对象
        webView.addJavascriptInterface(new AndroidtoJs(), "test");
        // 格式规定为:file:///android_asset/文件名.html(网络直接替换url)
        // webView.loadUrl("file:///android_asset/javascript.html");
        webView.loadUrl("https://www.jianshu.com/p/3c94ae673e2a");

        //TODO JS代码调用一定要在 onPageFinished（）回调之后才能调用
        webView.setWebViewClient(new WebViewClient() {
            /**
             * 开始加载
             * @param view
             * @param url
             * @param favicon
             */
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /**
             *加载结束
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideLoadingView();
            }

            /**
             * 加载错误
             * @param view
             * @param request
             * @param error
             */
            @Override public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });

        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(LoginActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });
    }

    /**
     * js调dndroid方法的映射类
     * 通过webView的addJavascriptInterface()方法映射到js中，再通过映射的对象调用方法
     */
    public static class AndroidtoJs   {

        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        public void hello(String msg) {
            Log.e("TAG", msg);
        }
    }

    /**
     * 返回登录Presenter对象
     *
     * @return
     */
    @Override protected MvpTestPresenter createPresenter() {
        return new MvpTestPresenter();
    }

    /**
     * 登录成功回调
     */
    @Override public void loginSuccess(Token token) {
        UserInfoUtils.saveToken(token);
        Toast.makeText(mContext, "登录成功,即将跳转", Toast.LENGTH_SHORT).show();
        //启动MainActivity
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
