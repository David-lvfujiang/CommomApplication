package com.nnmzkj.common.base;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.nnmzkj.common.mvp.IBaseView;
import com.nnmzkj.common.network.NetworkCallbackImpl;
import com.nnmzkj.common.network.NetworkChangeEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @ClassName: BaseFragment
 * @Description:
 * @Author: lvfujiang
 * @CreateDate: 2019/9/16
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    protected Context mContext;
    private Unbinder mUnbinder;
    private boolean isCreaedView = false;
    protected boolean isFirst = true;
    private NetworkCallbackImpl networkCallback;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isLazyLoading()) {
            if (isVisibleToUser) {
                //懒加载要满足三个条件才会执行：
                // 1.onCreateView()执行完成
                // 2.getUserVisibleHint()可见
                // 3.isFirst = ture
                LazyLoading();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        if (useEventbus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, rootView);
        isCreaedView = true;
        initView(rootView);
        if (isLazyLoading()) {
            LazyLoading();
        } else {
            initData();
        }
        return rootView;
    }

    @Override public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (networkCallback == null) {
                networkCallback = new NetworkCallbackImpl();
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connMgr != null) {
                    //安卓系统大于5.0小于7.0
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                        connMgr.requestNetwork(new NetworkRequest.Builder().build(), networkCallback);
                    } else {
                        //安卓系统大于7.0
                        connMgr.registerDefaultNetworkCallback(networkCallback);
                    }
                }
            }
        }
    }

    /**
     * 监听网络变化
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetworkChangeEvent(NetworkChangeEvent event) {
        if (event.isConnect()) {
            checkNetwork(event.isConnect());
        }
    }

    /**
     * 数据重连
     */
    private void checkNetwork(boolean isConnected) {
        if (isConnected) {
            initData();
        }
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        isFirst = true;
    }

    /**
     * 懒加载
     * 如果需要进行懒加载并且在第一次加载后不再重新加载则在请求成功后设置isFirst = false
     */
    protected void LazyLoading() {
        //onCreateView执行结束并且可见后再进行判断是否需要检测登录
        if (isCreaedView && getUserVisibleHint() && isFirst) {
            initData();
        }
    }

    /**
     * 是否支持懒加载
     *
     * @return
     */
    protected boolean isLazyLoading() {
        return true;
    }

    protected boolean useEventbus() {
        return true;
    }

    @Override public void showLoadingView() {
    }

    @Override public void hideLoadingView() {
    }

    @Override public void showNetWorkErrView() {
    }

    @Override public void gotoLoginActivity() {
    }

    @Override public void hideNetWorkErrView() {
    }

    @Override public void showNoDataView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (useEventbus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 获取布局文件id
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化view
     */
    protected abstract void initView(View rootView);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
