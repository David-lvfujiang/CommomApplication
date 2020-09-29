package com.nnmzkj.common.base;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 用于不是MVP的情况
 */
public abstract class BaseNetworkFragment  extends BaseFragment {

    private CompositeDisposable mCompositeDisposable; // 管理订阅关系，用于取消订阅

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
    public void onDestroyView() {
        super.onDestroyView();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }



}
