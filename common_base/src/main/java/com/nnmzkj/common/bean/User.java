package com.nnmzkj.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    /**
     * uid : 9
     * account : 18776965705
     * nickname : okcz
     * avatar : https://wx.qlogo.cn/mmopen/vi_32/Z9olBlRPnFetU6YgiaGGJrQd1nj20ibRDtFME7v6IuYDDjTkRNrUWIrrVbEycMRGJjicPtO8NaqiammekAD3knSgyg/132
     * phone : 18776965705
     * now_money : 3453.00
     * integral : 2068.00
     * sex : 2
     * collect : 5
     * coupon : 18
     */

    private int uid;
    private String account;
    private String nickname;
    private String avatar;
    private String phone;
    private String now_money;
    private String integral;
    private int sex;
    private int collect;
    private int coupon;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNow_money() {
        return now_money;
    }

    public void setNow_money(String now_money) {
        this.now_money = now_money;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getCollect() {
        return collect;
    }

    public void setCollect(int collect) {
        this.collect = collect;
    }

    public int getCoupon() {
        return coupon;
    }

    public void setCoupon(int coupon) {
        this.coupon = coupon;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.account);
        dest.writeString(this.nickname);
        dest.writeString(this.avatar);
        dest.writeString(this.phone);
        dest.writeString(this.now_money);
        dest.writeString(this.integral);
        dest.writeInt(this.sex);
        dest.writeInt(this.collect);
        dest.writeInt(this.coupon);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.uid = in.readInt();
        this.account = in.readString();
        this.nickname = in.readString();
        this.avatar = in.readString();
        this.phone = in.readString();
        this.now_money = in.readString();
        this.integral = in.readString();
        this.sex = in.readInt();
        this.collect = in.readInt();
        this.coupon = in.readInt();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override public User[] newArray(int size) {
            return new User[size];
        }
    };
}