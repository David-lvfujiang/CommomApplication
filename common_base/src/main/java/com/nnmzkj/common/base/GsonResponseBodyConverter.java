package com.nnmzkj.common.base;

import com.google.gson.Gson;
import com.nnmzkj.common.http.ApiException;
import java.io.IOException;
import java.lang.reflect.Type;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Converter;

/**
 * Date : 2020/4/17
 * Author : Davaid.lvfujiang
 * Desc :解析数据转换器,防止接口错误时返回的数据类型错误导致json解析失败
 * 例如login接口成功是返回token(String类型),失败则返回[],gson直接解析错误无法转换成BaseResponse,我们就无法读取message,不能准确提示错误给用户
 * 所以如果在这进行判断，当code!=200时则直接抛出ApiException
 */
public class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    public GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        int code = 0;
        String msg = "";
        try {
            //将返回的数据解析成json
            JSONObject jsonObject = new JSONObject(response);
            //获取状态码
            code = jsonObject.getInt("code");
            msg = jsonObject.getString("msg");
            if ("no".equals(msg)) {
                msg = jsonObject.getJSONObject("data").getJSONObject("data").getString("tips");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (code == 200) {
            //200的时候就直接解析成我们的实体
            return gson.fromJson(response, type);
        } else if (code == 403 || code == 402 || code == 400 || code == 404) {
            //抛出自定义异常
            throw new ApiException(code, msg);
        } else {
            throw new IOException();
        }
    }
}

