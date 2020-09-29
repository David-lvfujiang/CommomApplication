package com.nnmzkj.commomapplication.mvp.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nnmzkj.commomapplication.R;
import com.nnmzkj.commomapplication.mvp.adpter.ArticleAdapter;
import com.nnmzkj.common.base.BaseNetworkFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Date : 2020/9/10
 * Author : Davaid.lvfujiang
 * Desc :
 */
public class CommonFragment extends BaseNetworkFragment {
    @BindView(R.id.rv_content) RecyclerView recyclerView;
    List data = new ArrayList<String>();
    List<String> paths;

    @Override protected int getLayoutResId() {
        return R.layout.activity_scrolling;
    }

    @Override protected void initView(View rootView) {
        data.add("哈哈哈哈1");
        data.add("哈哈哈哈2");
        data.add("哈哈哈哈3");
        data.add("哈哈哈哈4");
        data.add("哈哈哈哈5");
        data.add("哈哈哈哈1");
        data.add("哈哈哈哈2");
        data.add("哈哈哈哈3");
        data.add("哈哈哈哈4");
        data.add("哈哈哈哈5");

        paths = new ArrayList<>();
        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=4a0b330b329a95207ac1c2db3431d554&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg");
        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=c1b5e6f3a67a0e389e3697e99576187a&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg");

        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=4a0b330b329a95207ac1c2db3431d554&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg");
        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=c1b5e6f3a67a0e389e3697e99576187a&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg");

        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=4a0b330b329a95207ac1c2db3431d554&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg");
        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=c1b5e6f3a67a0e389e3697e99576187a&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg");
        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=4a0b330b329a95207ac1c2db3431d554&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg");
        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=c1b5e6f3a67a0e389e3697e99576187a&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg");
        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=4a0b330b329a95207ac1c2db3431d554&imgtype=0&src=http%3A%2F%2Fa3.att.hudong.com%2F14%2F75%2F01300000164186121366756803686.jpg");
        paths.add(
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1601209837108&di=c1b5e6f3a67a0e389e3697e99576187a&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg");
    }

    @Override protected void initData() {
        View view = View.inflate(mContext, R.layout.item, null);

        ArticleAdapter articleAdapter = new ArticleAdapter(R.layout.item, paths);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerView.setAdapter(articleAdapter);
        articleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, TaskBigImgActivity.class);
                intent.putStringArrayListExtra("data", (ArrayList<String>) paths);
                intent.putExtra("index",position);
                getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) mContext, view.findViewById(R.id.image), "mybtn").toBundle());
            }
        });
    }
}
