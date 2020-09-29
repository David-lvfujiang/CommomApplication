package com.nnmzkj.commomapplication.mvp.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.nnmzkj.commomapplication.R;
import com.nnmzkj.common.base.BaseNetworkActivity;
import com.nnmzkj.common.manager.AppManager;
import java.util.ArrayList;

public class TaskBigImgActivity extends BaseNetworkActivity {

    /** 当前页 */
    @BindView(R.id.tv_current) TextView tvCurrent;
    /** 总页 */
    @BindView(R.id.tv_all) TextView tvAll;
    @BindView(R.id.big_img_vp)
    ViewPager bigImgVp;

    /** 索引 */
    private int index;
    /** 图片数组 */
    private ArrayList<String> paths = new ArrayList<>();

    @Override public int getLayoutResId() {
        return R.layout.activity_task_big_img;
    }

    @Override
    public void initView() {
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paths = getIntent().getStringArrayListExtra("data");
        index = getIntent().getIntExtra("index", 0);
        tvCurrent.setText((index + 1) + "");
        tvAll.setText(paths.size() + "");
        bigImgVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return paths == null ? 0 : paths.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View adView = LayoutInflater.from(TaskBigImgActivity.this).inflate(R.layout.item_big_img, null);
                PhotoView icon = (PhotoView) adView.findViewById(R.id.flaw_img);
                icon.setBackgroundColor(getResources().getColor(R.color.black));
                icon.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override public void onClick(View v) {
                        finishAfterTransition();
                        AppManager.getAppManager().removeActivity(TaskBigImgActivity.this);
                    }
                });
                Glide.with(TaskBigImgActivity.this)
                        .load(paths.get(position))
                        .into(icon);
                container.addView(adView);
                return adView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        bigImgVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvCurrent.setText((position + 1) + "");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bigImgVp.setCurrentItem(index, true);
    }

    @Override protected void initData() {

    }

}
