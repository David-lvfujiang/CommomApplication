package com.nnmzkj.common.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.nnmzkj.common.mvp.BasePresenter;

/**
 * @ClassName: BaseMVPActivity
 * @Description:
 * @Author: PZJ
 * @CreateDate: 2019/9/16
 */
public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this); // presenter 绑定 view
        }
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Activity 销毁时取消所有的订阅
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
