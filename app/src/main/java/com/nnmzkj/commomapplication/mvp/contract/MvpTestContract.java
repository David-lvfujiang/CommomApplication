package com.nnmzkj.commomapplication.mvp.contract;

import com.nnmzkj.common.bean.Token;
import com.nnmzkj.common.mvp.IBaseModel;
import com.nnmzkj.common.mvp.IBaseView;

/**
 * Date : 2020/6/2
 * Author : Davaid.lvfujiang
 * Desc :
 */
public interface MvpTestContract {
    interface Model extends IBaseModel {
    }

    interface View extends IBaseView {
        void loginSuccess(Token token);
    }

    interface Presenter {
        void httpRquest();
    }
}
