package com.nnmzkj.commomapplication.not.mvp;

import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nnmzkj.commomapplication.R;
import com.nnmzkj.commomapplication.util.ImageUtil;
import java.util.List;

/**
 * Date : 2020/6/11
 * Author : Davaid.lvfujiang
 * Desc : 图片上传
 */
public class UploadPictureAdapter extends BaseMultiItemQuickAdapter<MultiItemImgBean, BaseViewHolder> {

    public UploadPictureAdapter(List<MultiItemImgBean> data) {
        super(data);
        //必须绑定type和layout的关系
        addItemType(MultiItemImgBean.FIRST_TYPE, R.layout.item_upload);
        addItemType(MultiItemImgBean.SECOND_TYPE, R.layout.item_upload_default_item);
    }

    @Override protected void convert(@NonNull BaseViewHolder helper, MultiItemImgBean item) {
        switch (helper.getItemViewType()) {
            case MultiItemImgBean.FIRST_TYPE:
                ImageView imageView = helper.getView(R.id.img_content);
                ImageUtil.loadImage(item.getUrl(),imageView);
                helper.addOnClickListener(R.id.img_del);
                helper.addOnClickListener(R.id.img_content);
                break;
            case MultiItemImgBean.SECOND_TYPE:
                helper.addOnClickListener(R.id.img_add);
                break;
            default:
                break;
        }
    }
}