package com.nnmzkj.commomapplication.mvp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.jpeng.jptabbar.JPTabBar;
import com.jpeng.jptabbar.OnTabSelectListener;
import com.jpeng.jptabbar.anno.NorIcons;
import com.jpeng.jptabbar.anno.SeleIcons;
import com.jpeng.jptabbar.anno.Titles;
import com.nnmzkj.commomapplication.R;
import com.nnmzkj.commomapplication.mvp.adpter.ViewPagerAdapter;
import com.nnmzkj.commomapplication.util.ApiServiceUtil;
import com.nnmzkj.common.base.BaseFragment;
import com.nnmzkj.common.base.BaseNetworkActivity;
import com.nnmzkj.common.base.BaseObserver;
import com.nnmzkj.common.bean.Token;
import com.nnmzkj.common.http.RequestParam;
import com.nnmzkj.common.utils.ProgressDialogUtil;
import com.nnmzkj.common.utils.ToastUitl;
import com.nnmzkj.common.utils.UserInfoUtils;
import com.nnmzkj.common.view.LoadProgressDialog;
import com.nnmzkj.common.view.MultipleStatusView;
import java.util.ArrayList;

/**
 * Date : 2020/6/2
 * Author : Davaid.lvfujiang
 * Desc : 主界面
 */
public class MainActivity extends BaseNetworkActivity {
    @BindView(R.id.multiple_status_view) MultipleStatusView multipleStatusView;
    @BindView(R.id.view_pager) ViewPager mViewPager;
    @BindView(R.id.tabbar) JPTabBar tabbar;
    private LoadProgressDialog loadProgressDialog;

    Handler handler = new Handler();
    private ArrayList<BaseFragment> mFagments = new ArrayList<>();

    @Titles
    private static final String[] mTitles = { "广场", "公众号", "体系", "项目" };

    @NorIcons
    private static final int[] mSeleIcons = { R.mipmap.guangchang_s, R.mipmap.wechat_s, R.mipmap.tixi_s, R.mipmap.project_s };

    @SeleIcons
    private static final int[] mNormalIcons = { R.mipmap.guangchang, R.mipmap.wechat, R.mipmap.tixi, R.mipmap.project };

    @Override protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    //检查当前系统是否已开启暗黑模式
    public boolean getDarkModeStatus(Context context) {
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_YES;
    }

    @Override protected void initView() {

        tabbar.setTabListener(new OnTabSelectListener() {
            @Override public void onTabSelect(int index) {

            }

            @Override public boolean onInterruptSelect(int index) {
                return false;
            }
        });
        mFagments.add(new CommonFragment());
        mFagments.add(new CommonFragment());
        mFagments.add(new CommonFragment());
        mFagments.add(new CommonFragment());
        // 在activity中FragmentManager通过getSupportFragmentManager()去获取，如果在是在fragment中就需要通过getChildFragmentManager()
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), mFagments, mTitles);
        //设置主页缓存页数
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(adapter);
        tabbar.setContainer(mViewPager);
        View middleView = tabbar.getMiddleView();

    }

    @Override protected void initData() {

    }

    /**
     * 登录
     */
    private void login() {

        RequestParam param = new RequestParam();
        param.addParameter("username", "18776965705");
        param.addParameter("password", "123456");
        param.addParameter("login_type", 1);

        addSubscribe(ApiServiceUtil.apiService().login(param.getParameter()),
                new BaseObserver<Token>(MainActivity.this) {
                    @Override
                    protected void onSuccess(Token data) {
                        UserInfoUtils.saveToken(data);
                        ToastUitl.showSuccesToast(mContext, "登录成功,即将跳转");
                    }
                });
    }

    @Override public void showLoadingView() {
        super.showLoadingView();
        loadProgressDialog = ProgressDialogUtil.INSTANCE.getLoadingDialog(mContext, "登录中...");
        loadProgressDialog.show();
    }

    @Override public void hideLoadingView() {
        super.hideLoadingView();
        loadProgressDialog.dismiss();
    }

    @Override public void showNetWorkErrView() {
        multipleStatusView.showNoNetwork();
    }
}
