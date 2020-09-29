package com.nnmzkj.common.base;

/**
 * @ClassName: BaseResponse
 * @Description: 网络请求返回的数据，按格式统一包装成 BaseResponse 类
 * @Author: PZJ
 * @CreateDate: 2019/9/16
 */
public class BaseResponse<T extends Object> {
    private int code = -1;
    private String msg;
    private T data;
    private int count = 0;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public int getCount() {
        return count;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
