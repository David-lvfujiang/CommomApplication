package com.nnmzkj.commomapplication.not.mvp;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import lombok.Data;

/**
 * Date : 2020/3/30
 * Author : Davaid.lvfujiang
 * Desc : 图片
 */
@Data
public class MultiItemImgBean implements MultiItemEntity {

    public static final int FIRST_TYPE = 1;
    public static final int SECOND_TYPE = 2;
    private int type;
    String url;

    public MultiItemImgBean(int type, String url) {
        this.type = type;
        this.url = url;
    }

    @Override public int getItemType() {
        return type;
    }

}
