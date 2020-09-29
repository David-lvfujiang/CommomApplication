package com.nnmzkj.commomapplication.not.mvp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.app.SharedElementCallback;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.nnmzkj.commomapplication.R;
import com.nnmzkj.common.base.BaseNetworkActivity;
import com.nnmzkj.common.manager.AppManager;
import com.nnmzkj.common.utils.ToastUitl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;

/**
 * 记录进来的索引和当前显示的索引，因为当这俩者不同的时候我们要改变共享元素，
 * 比如说我们是点击第一张缩略图进入预览，此时共享元素是第一张图片，退出的时候页面是逐渐缩小到第一张图片的
 * 但假如当我们滑动ViewPager到第三张再退出页面的时候我们肯定是希望页面逐渐缩小到第三张图，此时我们就需要改变共享元素了，改成第三张图
 */

public class TaskBigImgActivity extends BaseNetworkActivity {
    protected static String TRANSITIONNAME = "mybtn";

    @BindView(R.id.tv_current) TextView tvCurrent;
    @BindView(R.id.tv_all) TextView tvAll;
    @BindView(R.id.big_img_vp) ViewPager bigImgVp;

    private PagerAdapter pagerAdapter;
    /** 当前索引 */
    private int index;
    /** 图片数组 */
    private ArrayList<String> paths = new ArrayList<>();
    /** 记录进入时的索引和退出的索引 */
    private int startPosition, currentPosition;

    /**
     * 共享元素动画监听，进入与退出都会回调
     */
    private SharedElementCallback callback = new SharedElementCallback() {
        @Override
        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            PhotoView sharedElement = bigImgVp.getChildAt(currentPosition).findViewById(R.id.flaw_img);
            if (startPosition != currentPosition) {
                names.clear();
                sharedElements.clear();
                String name = TRANSITIONNAME;
                names.add(name);
                sharedElements.put(name, sharedElement);
            }
        }
    };

    @Override public int getLayoutResId() {
        return R.layout.activity_task_big_img;
    }

    @Override
    public void initView() {

        paths = getIntent().getStringArrayListExtra("data");
        index = getIntent().getIntExtra("index", 0);
        startPosition = index;
        currentPosition = index;
        tvCurrent.setText((index + 1) + "");
        tvAll.setText(paths.size() + "");
        pagerAdapter = new PagerAdapter() {
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
                    @Override public void onClick(View v) {
                        currentPosition = position;
                        //调用finish()没效果
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
        };
        bigImgVp.setOffscreenPageLimit(paths.size());
        bigImgVp.setAdapter(pagerAdapter);

        bigImgVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                tvCurrent.setText((position + 1) + "");
                if (position == paths.size() - 1) {
                    ToastUitl.showToast(mContext, "这已经是最后一张");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bigImgVp.setCurrentItem(index, true);
    }

    @Override protected void initData() {
    }

    @Override public void onBackPressed() {
        finishAfterTransition();
        AppManager.getAppManager().removeActivity(TaskBigImgActivity.this);
    }

    @Override
    public void finishAfterTransition() {
        Intent data = new Intent();
        data.putExtra("startPosition", startPosition);
        data.putExtra("currentPosition", currentPosition);
        EventBus.getDefault().post(new LocationChangeEvent(startPosition, currentPosition));
        setResult(RESULT_OK, data);
        super.finishAfterTransition();
    }

    @OnClick(R.id.img_close)
    public void handleOnclick(View view) {
        if (view.getId() == R.id.img_close) {
            onBackPressed();
            AppManager.getAppManager().removeActivity(TaskBigImgActivity.this);
        }
    }
}
