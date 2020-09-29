package com.nnmzkj.common.base;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import com.cxz.wanandroid.ext.ExtKt;
import com.nnmzkj.common.R;
import com.nnmzkj.common.manager.AppManager;
import com.nnmzkj.common.mvp.IBaseView;
import com.nnmzkj.common.network.NetworkCallbackImpl;
import com.nnmzkj.common.network.NetworkChangeEvent;
import com.nnmzkj.common.utils.DialogSettingUtil;
import com.nnmzkj.common.utils.StatusBarUtils;
import com.nnmzkj.common.utils.UserInfoUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.nnmzkj.common.constants.Constants.LOGIN_ACTIVITY;
import static com.nnmzkj.common.constants.Constants.LOGIN_SUCCESS_CODE;

/**
 * Date : 2020/5/12
 * Author : Davaid.lvfujiang
 * Desc : 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView, BGASwipeBackHelper.Delegate {

    protected Context mContext;
    private Unbinder mUnbinder;
    /** 内容布局 */
    private FrameLayout mFlContent;
    protected BGASwipeBackHelper mSwipeBackHelper;
    private NetworkCallbackImpl networkCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        DialogSettingUtil.setting();
        mContext = this;
        try {
            setContentView(getLayoutResId());
            if (useEventbus()) {
                if (!EventBus.getDefault().isRegistered(mContext)) {
                    EventBus.getDefault().register(mContext);
                }
            }
            //判断是否开启沉浸式状态栏
            if (isToolbarImmersion()) {
                //开启沉浸式状态栏
                StatusBarUtils.setTransparent(this);
            }
            //绑定butterknife
            mUnbinder = ButterKnife.bind(this);
            //将当前activity添加到AppManager统一管理
            AppManager.getAppManager().addActivity(this);
            initView();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (networkCallback == null) {
                networkCallback = new NetworkCallbackImpl();
                ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
            ExtKt.showToast(mContext,"网络已连接");
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

    /**
     * 检查是否已经登录
     * 这里检查的是本地是否存在token，如果不存在就让跳转到登录页面
     * 还有一种情况是token存在但是过期了，当我们向后台进行网络请求时会返回token过期的状态码（402），我们就在BaseObserver的onError（）方法进行拦截跳转到登录页面
     *
     * @return
     */
    protected boolean isAlreadyLogin() {
        if (!UserInfoUtils.checkLoginStatusAvailable()) {
            gotoLoginActivity();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 去登录
     * 在这里我们只能通过反射去启动loginActivity
     */
    @Override public void gotoLoginActivity() {
        Intent intent = new Intent();
        try {
            //通过反射获取登录页面路径
            Class clazz = Class.forName(LOGIN_ACTIVITY);
            intent.setClass(AppManager.getAppManager().currentActivity(), clazz);
            intent.putExtra("request", LOGIN_SUCCESS_CODE);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //获取当前activity对象去启动登录页，回调成功时就可以调用initDate()重新加载数据
            AppManager.getAppManager().currentActivity().startActivityForResult(intent, LOGIN_SUCCESS_CODE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "请先登录账号", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showLoadingView(){}

    @Override
    public void hideLoadingView() {}

    @Override public void showNetWorkErrView() {}

    @Override
    public void hideNetWorkErrView() {}

    @Override public void showNoDataView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //反注册ButterKnife
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (useEventbus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    /**
     * 是否支持沉浸式状态栏
     *
     * @return
     */
    public boolean isToolbarImmersion() {
        return true;
    }

    protected boolean useEventbus() {
        return true;
    }

    /**
     * 获取布局文件id
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        AppManager.getAppManager().finishActivity();
        mSwipeBackHelper.swipeBackward();
    }

    @Override
    public void onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }
}
