package com.nnmzkj.common.base;

import android.content.Context;
import android.os.Bundle;
import com.nnmzkj.common.mvp.BasePresenter;

public abstract class BaseMVPFragment<P extends BasePresenter> extends BaseFragment{

    protected P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
    }

    /**
     * 获取 context
     *
     * @return
     */
    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * 创建 presenter
     *
     * @return
     */
    protected abstract P createPresenter();
}
