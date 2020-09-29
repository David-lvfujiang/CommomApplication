package com.nnmzkj.common.base;

import android.app.Activity;
import com.cxz.wanandroid.ext.ExtKt;
import com.kongzue.dialog.v3.WaitDialog;
import com.nnmzkj.common.constants.Constants;
import com.nnmzkj.common.http.ApiException;
import com.nnmzkj.common.http.ExceptionHandler;
import com.nnmzkj.common.mvp.IBaseView;
import com.nnmzkj.common.utils.LogUtils;
import io.reactivex.observers.DisposableObserver;

import static com.nnmzkj.common.http.Constant.Code.FAILURE;
import static com.nnmzkj.common.http.Constant.Code.INTERNAL_SERVER_ERROR;
import static com.nnmzkj.common.http.Constant.Code.NET_ERROR;
import static com.nnmzkj.common.http.Constant.Code.NOT_FOUND;
import static com.nnmzkj.common.http.Constant.Code.NOT_LOGGED;
import static com.nnmzkj.common.http.Constant.Code.NO_DATA;
import static com.nnmzkj.common.http.Constant.Code.PARSING_ERROR;
import static com.nnmzkj.common.http.Constant.Code.TOKEN_INVALID;

public abstract class BaseObserver<T> extends DisposableObserver<BaseResponse<T>> {
    private IBaseView baseView;
    private boolean isShowLoad = true;

    /**
     * 默认构造函数
     */
    public BaseObserver() {
    }

    /**
     * 如果在请求时需要进行额外处理，例如显示loading,则使用以下构造函数
     *
     * @param baseView
     */
    public BaseObserver(IBaseView baseView) {
        this.baseView = baseView;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (baseView != null) {
            baseView.showLoadingView();
        }
    }

    @Override
    public void onNext(BaseResponse<T> baseResponse) {
        if (baseView != null) {
            //网络请求成功，关闭等待loding
            baseView.hideLoadingView();
            WaitDialog.dismiss();
        }
        int code = baseResponse.getCode();
        String msg = baseResponse.getMsg();
        //数据请求成功，code==200
        if (code == Constants.HttpCode.SUCCESS) {
            onSuccess(baseResponse.getData());
        } else {
            //数据请求成功，但是code != 200
            LogUtils.e(code + msg);
            //创建自定义异常
            onError(new ApiException(code, msg));
        }
    }

    /**
     * 异常处理，包括两方面数据：
     * (1) 服务端没有没有返回数据，请求直接抛出异常，不走onNext()方法，例如HttpException、json解析错误等;
     * (2) 服务端返回了数据，执行了onNext()方法，但 code!=200, 例如密码错误code==400,token失效code ==402等;
     */
    @Override
    public void onError(Throwable e) {
        //对Exception再次处理简化
        ExceptionHandler.ResponeThrowable responeThrowable = ExceptionHandler.handleException(e);
        if (baseView != null) {
            baseView.hideLoadingView();
        }
        switch (responeThrowable.getCode()) {
            //400 (请求失败)
            case FAILURE:
                defail(responeThrowable);
                break;
            //402，403，404 (登录错误相关)
            case NOT_LOGGED:
            case TOKEN_INVALID:
            case NOT_FOUND:
                //跳转用户登录界面
                baseView.gotoLoginActivity();
                break;
            // 无数据
            case NO_DATA:
                noData();
                break;
            //无网络
            case NET_ERROR:
            case INTERNAL_SERVER_ERROR:
                noNet();
                break;
            //json解析错误
            case PARSING_ERROR:
                ExtKt.showToast((Activity) baseView, responeThrowable.getMessage());
                break;
            default:
                break;
        }
    }

    /**
     * 回调正常数据
     *
     * @param data data
     */
    protected abstract void onSuccess(T data);

    /**
     * 失败、无数据
     */
    protected void noData() {
        if (baseView!=null){
            baseView.showNoDataView();
        }
    }

    /**
     * 无网络
     */
    protected void noNet() {
        ExtKt.showToast((Activity) baseView, "请检查您的网络");
        if (baseView!=null){
            baseView.showNetWorkErrView();
        }

    }

    /**
     * 400,服务端请求失败
     */
    protected void defail(ExceptionHandler.ResponeThrowable responeThrowable) {
        ExtKt.showToast((Activity) baseView, responeThrowable.getMessage());

    }

    @Override
    public void onComplete() {
        if (baseView != null) {
            baseView.hideLoadingView();
        }
    }
}
