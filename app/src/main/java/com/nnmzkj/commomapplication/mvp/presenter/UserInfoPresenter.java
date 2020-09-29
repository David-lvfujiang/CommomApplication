package com.nnmzkj.commomapplication.mvp.presenter;

import com.nnmzkj.commomapplication.mvp.contract.UserInfoContract;
import com.nnmzkj.commomapplication.mvp.model.UserInfoModel;
import com.nnmzkj.commomapplication.util.ApiServiceUtil;
import com.nnmzkj.common.base.BaseObserver;
import com.nnmzkj.common.http.RequestParam;
import com.nnmzkj.common.mvp.BasePresenter;

/**
 * Date : 2020/6/2
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class UserInfoPresenter extends BasePresenter<UserInfoContract.Model, UserInfoContract.View> implements UserInfoContract.Presenter {

    @Override protected UserInfoContract.Model createModel() {
        return new UserInfoModel();
    }

    /**
     * 获取用户信息
     */
    @Override public void getUserInfo() {
        RequestParam param = new RequestParam();
        addSubscribe(ApiServiceUtil.userService().userInfo(param.getParameter()),
                new BaseObserver<UserInfoModel>(mView) {
                    @Override
                    protected void onSuccess(UserInfoModel data) {
                        if (data != null) {
                            mModel = data;
                            //ui界面刷新用户信息回调
                            mView.showUserInfo(data);
                        }
                    }
                });
    }
}
