package com.nnmzkj.commomapplication.mvp.model;

import com.nnmzkj.commomapplication.mvp.contract.MvpTestContract;
import lombok.Data;

/**
 * Date : 2020/6/2
 * Author : Davaid.lvfujiang
 * Desc :
 */
@Data
public class MvpTestModel implements MvpTestContract.Model {
    public MvpTestModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    int id;
    String name;
}
