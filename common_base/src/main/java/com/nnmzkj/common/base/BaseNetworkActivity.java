package com.nnmzkj.common.base;

import android.content.Context;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Date : 2020/5/12
 * Author : Davaid.lvfujiang
 * Desc : 非MVP模式
 */
public abstract class BaseNetworkActivity extends BaseActivity {
    /** 管理订阅关系，用于取消订阅 */
    private CompositeDisposable mCompositeDisposable;

    /**
     * 添加订阅
     */
    protected void addSubscribe(Observable<?> observable, BaseObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        BaseObserver baseObserver =
                observable.subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(observer);
        mCompositeDisposable.add(baseObserver);
    }

    @Override
    protected void onDestroy() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
