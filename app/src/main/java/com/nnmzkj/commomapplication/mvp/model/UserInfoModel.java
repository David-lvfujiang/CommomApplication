package com.nnmzkj.commomapplication.mvp.model;

import com.nnmzkj.commomapplication.mvp.contract.UserInfoContract;
import lombok.Data;
import org.parceler.Parcel;

/**
 * Date : 2020/6/2
 * Author : Davaid.lvfujiang
 * Desc :
 */
@Data
@Parcel
public class UserInfoModel implements UserInfoContract.Model {
     int uid;
     String account;
     String nickname;
     String avatar;
     String phone;
     String now_money;
     String integral;
     int sex;
     int collect;
     int coupon;
}
