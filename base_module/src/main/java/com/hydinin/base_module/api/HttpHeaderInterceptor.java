package com.hydinin.base_module.api;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio
 * Project：ZhiChuang
 * Author：httvc
 * Email：jfjie2013@163.com
 * Date：2018/3/20.
 * 请求头拦截器
 */

public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //  配置请求头
        String accessToken = "token";
        String tokenType = "tokenType";
       // accessToken= (String) SPUtil.get(BaseApplication.getInstance(), Constant.UserInfo.TOKEN,"");
        Request request = chain.request().newBuilder()
               // .header("app_key", "appId")
                .header("Authorization", accessToken)
                .header("Content-Type", "application/json")
               // .header("token",accessToken)
                .addHeader("Connection", "close")
                .addHeader("Accept-Encoding", "identity")
                .build();
        return chain.proceed(request);
    }
}
