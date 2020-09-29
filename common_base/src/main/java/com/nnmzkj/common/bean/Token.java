package com.nnmzkj.common.bean;

public class Token {

    private Integer uid;
    private String token;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override public String toString() {
        return "Token{" +
                "uid='" + uid + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
