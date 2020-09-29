package com.nnmzkj.commomapplication.apiservice;

import com.nnmzkj.common.base.BaseResponse;
import com.nnmzkj.common.bean.Token;
import io.reactivex.Observable;
import java.util.Map;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 数据接口
 */
public interface ApiService {

    /**
     * 接口名称:登录 接口地址:/appapi/Login/login
     * 请求方法:POST
     * 参数名称 	    是否必须 	数据类型 	默认值 	描述
     * username 	true 	    string 		        手机号码
     * password 	true 	    string 		        密码
     */
    @POST("appapi/Login/login")
    @FormUrlEncoded
    Observable<BaseResponse<Token>> login(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:注册 接口地址:/appapi/Login/register
     * 请求方法:POST
     * 参数名称 	                是否必须 	数据类型 	默认值 	描述
     * username 	            true 	    string 		        手机号码
     * password 	            true 	    string 		        密码
     * verification_password 	true 	    string 		        确认密码
     * verification_code 	    true 	    string 		        手机验证码
     */
    @POST("appapi/Login/register")
    @FormUrlEncoded
    Observable<BaseResponse> register(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:忘记密码 接口地址:/appapi/Login/forgotPwd
     * 请求方法:POST
     * 参数名称 	            是否必须 	数据类型 	默认值 	描述
     * username 	        true 	    string 		        手机号码
     * code 	            true 	    string 		        验证码
     * new_password 	    true 	    string 		        新密码
     * vverify_password 	true 	    string 		        确认密码
     */
    @POST("appapi/Login/forgotPwd")
    @FormUrlEncoded
    Observable<BaseResponse> forgetPassword(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:发送验证码 接口地址:/appapi/Verification_code/apiSend
     * 请求方法:POST
     * 参数名称 	是否必须 	数据类型 	默认值 	描述
     * mobile 	true 	    string 		        手机号码
     */
    @POST("appapi/Verification_code/apiSend")
    @FormUrlEncoded
    Observable<BaseResponse> getVerificationCode(@FieldMap Map<String, Object> params);


}
