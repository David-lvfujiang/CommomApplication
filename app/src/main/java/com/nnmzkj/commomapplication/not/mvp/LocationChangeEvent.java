package com.nnmzkj.commomapplication.not.mvp;

import lombok.Data;

/**
 * Date : 2020/9/28
 * Author : Davaid.lvfujiang
 * Desc :
 */
@Data
public class LocationChangeEvent {
    private int startPosition,currentPosition;

    public LocationChangeEvent(int startPosition, int currentPosition) {
        this.startPosition = startPosition;
        this.currentPosition = currentPosition;
    }
}
