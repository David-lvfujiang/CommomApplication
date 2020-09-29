package com.nnmzkj.common.mvp;

import android.content.Context;
import com.nnmzkj.common.base.BaseObserver;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.lang.ref.WeakReference;

/**
 * @ClassName: BasePresenter
 * @Description: 所有Presenter基类
 * @Author: PZJ
 * @CreateDate: 2019/9/16
 */
public abstract class BasePresenter<M extends IBaseModel, V extends IBaseView> {

    protected V mView;
    protected M mModel;
    private WeakReference<V> mWeakReference;
    private CompositeDisposable mCompositeDisposable; // 管理订阅关系，用于取消订阅

    /**
     * 绑定View，一般在初始化时调用
     */
    public void attachView(V view) {
        this.mWeakReference = new WeakReference<>(view);
        this.mView = mWeakReference.get();
        if (this.mModel == null) {
            this.mModel = createModel();
        }
    }

    /**
     * 解除绑定 View,一般在 onDestroy 中调用
     */
    public void detachView() {
        this.mModel = null;
        if (isViewAttached()) {
            this.mWeakReference.clear();
            this.mWeakReference = null;
        }
        unsubscribe();
    }

    /**
     * 是否绑定了View，一般在发起网络请求之前调用
     *
     * @return
     */
    protected boolean isViewAttached() {
        return this.mWeakReference != null && this.mWeakReference.get() != null;
    }

    /**
     * 获取view
     *
     * @return
     */
    protected V getView() {
        return this.mView;
    }

    /**
     * 获取model
     *
     * @return
     */
    protected M getModel() {
        return this.mModel;
    }

    /**
     * 获取context
     *
     * @return
     */
    protected Context getContext() {
        return getView().getContext();
    }

    /**
     * 添加订阅
     */
    protected void addSubscribe(Observable<?> observable, BaseObserver observer) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        BaseObserver baseObserver =
                observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(observer);
        mCompositeDisposable.add(baseObserver);
    }

    /**
     * 取消订阅
     */
    protected void unsubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
        }
    }

    /**
     * 创建Model
     *
     * @return
     */
    protected abstract M createModel();

    //protected <T> T create(Class<T> clazz) {
    //    return RetrofitClient.getInstance().getRetrofit().create(clazz);
    //}
}