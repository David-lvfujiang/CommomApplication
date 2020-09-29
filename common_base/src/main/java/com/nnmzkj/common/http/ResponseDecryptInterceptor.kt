package com.nnmzkj.common.http

import com.nnmzkj.common.utils.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.nio.charset.Charset

/**
 *Date : 2020/9/24
 *Author : Davaid.lvfujiang
 *Desc :对响应数据进行解密
 */

class ResponseDecryptInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        var response = chain.proceed(request)

        if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                /*开始解密*/
                try {
                    val source = responseBody.source()
                    source.request(java.lang.Long.MAX_VALUE)
                    val buffer = source.buffer()
                    var charset = Charset.forName("UTF-8")
                    val contentType = responseBody.contentType()
                    if (contentType != null) {
                        charset = contentType.charset(charset)
                    }
                    /*服务端返回的密文,一般是String字符串,若是json格式则自行提取data进行解密*/
                    val bodyString = buffer.clone()
                            .readString(charset)
                    val responseData = "这里调解密的方法"
                    /*将解密后的明文返回*/
                    val newResponseBody = ResponseBody.create(contentType, responseData.trim())
                    response = response.newBuilder()
                            .body(responseBody)
                            .build()
                } catch (e: Exception) {
                    /*异常说明解密失败 信息被篡改 直接返回即可 */
                    LogUtils.e("解密异常====》${e}")
                    return response
                }
            } else {
                LogUtils.i("响应体为空")
            }
        }
        return response

    }
}
