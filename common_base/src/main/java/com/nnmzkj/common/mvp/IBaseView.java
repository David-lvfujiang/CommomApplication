package com.nnmzkj.common.mvp;

import android.content.Context;

/**
 * @ClassName: IBaseView
 * @Description: 所有View基类
 * @Author: PZJ
 * @CreateDate: 2019/9/16
 */
public interface IBaseView {

    public void showLoadingView();

    public void hideLoadingView();

    public void showNetWorkErrView();

    public void hideNetWorkErrView();

    public void showNoDataView();


    public void gotoLoginActivity();



    /**
     * 上下文
     *
     * @return context
     */
    Context getContext();
}
