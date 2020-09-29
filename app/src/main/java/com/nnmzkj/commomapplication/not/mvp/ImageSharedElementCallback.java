package com.nnmzkj.commomapplication.not.mvp;

import android.view.View;
import androidx.core.app.SharedElementCallback;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Map;

/**
 * Date : 2020/9/28
 * Author : Davaid.lvfujiang
 * Desc :共享元素动画回调类
 */
public class ImageSharedElementCallback extends SharedElementCallback {
    protected String TRANSITIONNAME = "mybtn";
    private RecyclerView rvContent;
    private LocationChangeEvent locationChangeEvent;



    /**
     * 装载共享元素
     *
     * @param names
     * @param sharedElements
     */
    @Override
    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
        if (locationChangeEvent != null) {
            int startingPosition = locationChangeEvent.getStartPosition();
            int currentPosition = locationChangeEvent.getCurrentPosition();
            if (startingPosition != currentPosition) {
                //当退出预览时的索引和进入的索引不同时，我们需要更改共享元素
                View newSharedElement = rvContent.getChildAt(currentPosition);
                String newTransitionName = TRANSITIONNAME;
                if (newSharedElement != null) {
                    names.clear();
                    names.add(newTransitionName);
                    sharedElements.clear();
                    sharedElements.put(newTransitionName, newSharedElement);
                }
            }
            locationChangeEvent = null;
        }
    }

    public RecyclerView getRvContent() {
        return rvContent;
    }

    public void setRvContent(RecyclerView rvContent) {
        this.rvContent = rvContent;
    }

    public LocationChangeEvent getLocationChangeEvent() {
        return locationChangeEvent;
    }

    public void setLocationChangeEvent(LocationChangeEvent locationChangeEvent) {
        this.locationChangeEvent = locationChangeEvent;
    }
}
