package com.nnmzkj.commomapplication.not.mvp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kongzue.dialog.interfaces.OnMenuItemClickListener;
import com.kongzue.dialog.v3.BottomMenu;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.nnmzkj.commomapplication.R;
import com.nnmzkj.commomapplication.util.GlideEngine;
import com.nnmzkj.common.base.BaseNetworkActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Main3Activity extends BaseNetworkActivity {
    private static final int MAX_SELECT_NUMBER = 5;
    private static final int MAX_ROW_DISPLAY_NUMBER = 3;
    @BindView(R.id.rv_content) RecyclerView rvContent;

    private UploadPictureAdapter adapter;
    List<LocalMedia> selectList;
    private ArrayList<MultiItemImgBean> multiItemImgBeans = new ArrayList<MultiItemImgBean>();
    ArrayList<String> imgInfoList = new ArrayList<>();
    private LocationChangeEvent locationChangeEvent;
    private ImageSharedElementCallback sharedElementCallback;

    @Override protected int getLayoutResId() {
        return R.layout.activity_main3;
    }

    @Override
    protected void initView() {
        sharedElementCallback = new ImageSharedElementCallback();
        sharedElementCallback.setRvContent(rvContent);
        ActivityCompat.setExitSharedElementCallback((Activity) mContext, sharedElementCallback);
    }

    @Override protected boolean useEventbus() {
        return true;
    }

    /**
     * 监听预览图片位置改变事件,当我们退出照片预览页面时evenbus会发送通知传出我们进入预览时的索引和退出时的索引
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageEvent(LocationChangeEvent event) {
        locationChangeEvent = event;
        sharedElementCallback.setLocationChangeEvent(locationChangeEvent);
    }

    @Override protected void initData() {
        displayImg();
    }

    /**
     * 初始化图片
     */
    public void displayImg() {

        multiItemImgBeans.add(new MultiItemImgBean(MultiItemImgBean.SECOND_TYPE, ""));
        adapter = new UploadPictureAdapter(multiItemImgBeans);
        rvContent.setLayoutManager(new StaggeredGridLayoutManager(MAX_ROW_DISPLAY_NUMBER, StaggeredGridLayoutManager.VERTICAL));
        rvContent.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MultiItemImgBean multiItemImgBean = multiItemImgBeans.get(position);
                switch (view.getId()) {
                    case R.id.img_add:
                        ArrayList list = new ArrayList();
                        list.add("相机");
                        list.add("相册");
                        BottomMenu.show((AppCompatActivity) mContext, list, new OnMenuItemClickListener() {
                            @Override
                            public void onClick(String text, int index) {
                                if (index == 0) {
                                    openCamera();
                                } else {
                                    openAlbum();
                                }
                            }
                        });

                        break;
                    case R.id.img_content:
                        previewImg(position, multiItemImgBeans, view);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isCompress(true)
                .forResult(PictureConfig.REQUEST_CAMERA);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                //选择数量
                .maxSelectNum(MAX_SELECT_NUMBER)
                .isCompress(true)
                //每行显示最大数量
                .imageSpanCount(MAX_ROW_DISPLAY_NUMBER)
                .previewImage(true)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    /**
     * 预览照片
     *
     * @param position 位置
     * @param list 数组
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) public void previewImg(int position, List<MultiItemImgBean> list, View view) {
        imgInfoList.clear();
        for (MultiItemImgBean bean : list) {
            if (!StringUtils.isEmpty(bean.getUrl())) {
                imgInfoList.add(bean.getUrl());
            }
        }
        Intent intent = new Intent(mContext, TaskBigImgActivity.class);
        intent.putStringArrayListExtra("data", (ArrayList<String>) imgInfoList);
        intent.putExtra("index", position);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
                (Activity) mContext, view, TaskBigImgActivity.TRANSITIONNAME).toBundle());
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == RESULT_OK) {
            selectList = PictureSelector.obtainMultipleResult(data);
            Iterator iterator = selectList.iterator();
            while (iterator.hasNext()) {
                try {
                    MultiItemImgBean imgBean = null;
                    //获取压缩的路径
                    LocalMedia localMedia = (LocalMedia) iterator.next();
                    if (localMedia.getCompressPath() != null) {
                        imgBean = new MultiItemImgBean(MultiItemImgBean.FIRST_TYPE, localMedia.getCompressPath());
                    } else {
                        imgBean = new MultiItemImgBean(MultiItemImgBean.FIRST_TYPE, localMedia.getRealPath());
                    }
                    multiItemImgBeans.add(0, imgBean);
                    adapter.notifyDataSetChanged();
                } catch (NullPointerException e) {
                }
            }
        }
    }
}



