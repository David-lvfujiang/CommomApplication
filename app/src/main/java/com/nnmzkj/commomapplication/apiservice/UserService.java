package com.nnmzkj.commomapplication.apiservice;

import com.nnmzkj.commomapplication.mvp.model.UserInfoModel;
import com.nnmzkj.common.base.BaseResponse;
import io.reactivex.Observable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserService {

    /**
     * 接口名称:获取用户信息 接口地址:/appapi/user_api/getUserInfo
     * 请求方法:POST
     * 参数名称 	是否必须 	数据类型 	默认值 	描述
     */
    @POST("appapi/user_api/getUserInfo")
    @FormUrlEncoded
    Observable<BaseResponse<UserInfoModel>> userInfo(@FieldMap Map<String, Object> params);


    /**
     * 接口名称:添加修改户地址 接口地址:/appapi/User_api/edit_user_address
     * 请求方法:POST
     * 参数名称 	    是否必须 	数据类型 	默认值 	描述
     * post_code 	false 	    string 		        邮编
     * phone 	    true 	    string 		        电话
     * detail 	    true 	    string 		        详细地址
     * id 	        false 	    number 		        地址id 不传为添加 传为修改
     * address 	    true 	    object 		        地址实体
     * province 	true 	    string 	        	省
     * city 	    true 	    string 	        	市
     * district 	true    	string 	        	区
     * town 	    true 	    string 		        镇/街道办
     * street 	    false   	string 	        	街道/村
     * housenumber 	false 	    string 		        门牌号
     * is_default 	false 	    boolean 		    是否默认 否0 是1
     * real_name 	true 	    string 		        联系人
     */
    @POST("appapi/User_api/edit_user_address")
    Observable<BaseResponse> commitUserAddress(@Body RequestBody body);

    /**
     * 接口名称:删除地址 接口地址:/appapi/User_api/remove_user_address
     * 请求方法:POST
     * 参数名称 	    是否必须 	数据类型 	默认值 	描述
     * addressId 	true 	    string 		        地址id
     */
    @POST("appapi/User_api/remove_user_address")
    @FormUrlEncoded
    Observable<BaseResponse> deleteUserAddress(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:修改用户密码 接口地址:appapi/User_api/updateUserInfo
     * 请求方法:POST
     * 参数名称 	    是否必须 	数据类型 	默认值 	描述
     * sex 	        false 	    string 		        性别 1男 2女
     * nickname 	true 	    string 		        用户昵称
     */
    @POST("appapi/User_api/updateUserInfo")
    @FormUrlEncoded
    Observable<BaseResponse> updateUserInfo(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:修改用户密码 接口地址:/appapi/User_api/modifyPwd
     * 请求方法:POST
     * 参数名称 	       是否必须     	数据类型 	默认值 	描述
     * old_password     false 	    string 		        旧密码
     * old_password 	true 	    string 		        新密码
     * old_password 	true 	    string 		        确认密码
     */
    @POST("appapi/User_api/modifyPwd")
    @FormUrlEncoded
    Observable<BaseResponse> updateUserPassword(@FieldMap Map<String, Object> params);



    /**
     * 接口名称:签到 接口地址:/appapi/User_api/user_sign
     * 请求方法:POST
     * 参数名称 	是否必须 	数据类型 	默认值 	描述
     */
    @POST("appapi/User_api/user_sign")
    @FormUrlEncoded
    Observable<BaseResponse> userSign(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:修改头像 接口地址:/appapi/user_api/edit_user_avatar
     * 请求方法:POST
     * 参数名称 	是否必须 	数据类型 	默认值 	描述
     * avatar   true        file                图片文件
     * filename true        String              "avatar"
     */
    @Multipart
    @POST("appapi/user_api/edit_user_avatar")
    Observable<BaseResponse> editUserAvatar(@Part("filename") RequestBody filename, @Part MultipartBody.Part part);

    /**
     * 接口名称:用户意见反馈 接口地址:/appapi/user_api/feedback
     * 请求方法:POST
     * 参数名称     	是否必须 	数据类型 	默认值 	描述
     * content      true        string              反馈内容
     * image true   true        array[string]       ["1.jpg","2.jpg"]
     */
    @POST("appapi/user_api/feedback")
    @FormUrlEncoded
    Observable<BaseResponse> userFeedback(@FieldMap Map<String, Object> params, @Field("image[]") List<String> images);


    /**
     * 接口名称:添加抬头 接口地址:/appapi/sanran_api/addInvoiceMaterial
     * 请求方法:POST
     * 参数名称         	是否必须 	数据类型 	默认值 	描述
     * taxnumber        true        string              纳税人识别号
     * name             true        string              名称
     * addresstelephone true        string              地址电话
     * bankaccount      true        string              开户行账号
     * contact          true        string              接收发票 手机或电邮
     */
    @POST("appapi/sanran_api/addInvoiceMaterial")
    @FormUrlEncoded
    Observable<BaseResponse> addInvoiceMaterial(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:修改抬头 接口地址:/appapi/sanran_api/updateInvoiceMaterial
     * 请求方法:POST
     * 参数名称 	        是否必须 	数据类型 	默认值 	描述
     * id               true        string              抬头id
     * taxnumber        true        string              纳税人识别号
     * name             true        string              名称
     * addresstelephone true        string              地址电话
     * bankaccount      true        string              开户行账号
     * contact          true        string              接收发票 手机或电邮
     * state            true        string              接收发票 手机或电邮
     */
    @POST("appapi/sanran_api/updateInvoiceMaterial")
    Observable<BaseResponse> updateInvoiceMaterial(@Body RequestBody body);

    /**
     * 接口名称:开具发票 接口地址:/appapi/sanran_api/drawInvoice
     * 请求方法:POST
     * 参数名称 	        是否必须 	数据类型 	默认值 	描述
     * serial           true        string              发票单号
     * uuid             true        string               uuid
     * taxnumber        true        string              纳税人识别号
     * name             true        string              名称
     * addresstelephone true        string              地址电话
     * bankaccount      true        string              开户行账号
     * contact          true        string              接收发票 手机或电邮
     */
    @POST("appapi/sanran_api/drawInvoice")
    @FormUrlEncoded
    Observable<BaseResponse> drawInvoice(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:下载发票 接口地址:/appapi/sanran_api/downloadInvoice
     * 请求方法:POST
     * 参数名称 	        是否必须 	数据类型 	默认值 	描述
     * invoicecode      true        string              发票代码
     * invoicenumber    true        string              发票号码
     * total            true        string              价税合计
     */
    @POST("appapi/sanran_api/downloadInvoice")
    @FormUrlEncoded
    Observable<BaseResponse<HashMap<String, String>>> downloadInvoice(@FieldMap Map<String, Object> params);


    /**
     * 接口名称:绑定三燃 接口地址:/appapi/sanran_api/applyBindId
     * 请求方法:POST
     * 参数名称 	    是否必须 	数据类型 	默认值 	描述
     * name         true        string              姓名
     * telephone    true        string              电话
     * memberid     true        string              会员名
     * address      true        string              地址
     */
    @POST("appapi/sanran_api/applyBindId")
    @FormUrlEncoded
    Observable<BaseResponse> applyBindId(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:取消绑定三燃 接口地址:/appapi/sanran_api/unBindId
     * 请求方法:POST
     * 参数名称 	是否必须 	数据类型 	默认值 	描述
     */
    @POST("appapi/sanran_api/unBindId")
    @FormUrlEncoded
    Observable<BaseResponse> unBindId(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:初始化聊天通讯 接口地址:http://chat.sanrangas.com/init
     * 请求方法:POST
     * 参数名称 	      是否必须 	  数据类型 	默认值 	描述
     * client_id      true        string            连接ws成功后返回client_id
     * snsno          true        string            平台用户id
     * name           true        string            平台用户昵称
     */
    @POST("init")
    @FormUrlEncoded
    Observable<BaseResponse> initChat(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:初始化聊天通讯 接口地址:http://chat.sanrangas.com/say
     * 请求方法:POST
     * 参数名称 	    是否必须 	数据类型 	默认值 	描述
     * content      true        string              聊天内容
     * user_id      true        string              客服id
     * snsno        true        string              平台用户id
     */
    @POST("say")
    @FormUrlEncoded
    Observable<BaseResponse> say(@FieldMap Map<String, Object> params);

    /**
     * 接口名称:初始化聊天通讯 接口地址:http://chat.sanrangas.com/photo
     * 请求方法:POST
     * 参数名称 	    是否必须 	数据类型 	默认值 	描述
     * image        true        file                图片
     * snsno        true        string              平台用户id
     */
    @POST("photo")
    @Multipart
    Observable<BaseResponse> photo(@Part MultipartBody.Part part, @Part("snsno") RequestBody snsno);

    /**
     * 接口名称:获取历史消息例句 接口地址:http://chat.sanrangas.com/getchatmsg
     * 请求方法:POST
     * 参数名称 	是否必须 	数据类型 	默认值 	描述
     * p        true        number              从第几条开始返回20条
     * snsno    true        string              平台用户id
     */
    @POST("getchatmsg")
    @FormUrlEncoded
    Observable<BaseResponse<String>> getChatHistory(@FieldMap Map<String, Object> params);
}
