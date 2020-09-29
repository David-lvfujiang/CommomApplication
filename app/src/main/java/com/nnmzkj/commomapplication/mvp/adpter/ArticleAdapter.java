package com.nnmzkj.commomapplication.mvp.adpter;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nnmzkj.commomapplication.R;
import com.nnmzkj.common.manager.AppManager;
import java.util.List;

/**
 * Date : 2020/3/7
 * Author : Davaid.lvfujiang
 * Desc : 文章列表适配器
 */
public class ArticleAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public ArticleAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);

    }

    @Override protected void convert(@NonNull final BaseViewHolder helper, String item) {
        ImageView imageView = helper.getView(R.id.image);
        Glide.with(AppManager.getAppManager().currentActivity())
                .load(item)
                .into(imageView);

    }
}
