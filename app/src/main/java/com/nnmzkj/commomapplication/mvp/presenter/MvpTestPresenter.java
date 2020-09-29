package com.nnmzkj.commomapplication.mvp.presenter;

import android.util.Log;
import com.nnmzkj.commomapplication.mvp.contract.MvpTestContract;
import com.nnmzkj.commomapplication.util.ApiServiceUtil;
import com.nnmzkj.common.base.BaseObserver;
import com.nnmzkj.common.bean.Token;
import com.nnmzkj.common.http.RequestParam;
import com.nnmzkj.common.mvp.BasePresenter;

/**
 * Date : 2020/6/2
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class MvpTestPresenter extends BasePresenter<MvpTestContract.Model, MvpTestContract.View> implements MvpTestContract.Presenter {
    /**
     * 登录业务逻辑
     */
    @Override public void httpRquest() {
        RequestParam param = new RequestParam();
        param.addParameter("username", "18776965705");
        param.addParameter("password", "123456");
        addSubscribe(ApiServiceUtil.apiService().login(param.getParameter()),
                new BaseObserver<Token>(mView) {
                    @Override
                    protected void onSuccess(Token data) {
                        Log.e("tag", data.getToken());
                        //界面刷新回调
                        mView.loginSuccess(data);
                    }
                });
    }

    @Override protected MvpTestContract.Model createModel() {
        return null;
    }
}
