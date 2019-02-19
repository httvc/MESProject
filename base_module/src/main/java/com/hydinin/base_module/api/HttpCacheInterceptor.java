package com.hydinin.base_module.api;

import android.text.TextUtils;

import com.hydinin.base_module.BaseApplication;
import com.hydinin.base_module.utils.NetworkUtils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Android Studio
 * Project：ZhiChuang
 * Author：httvc
 * Email：jfjie2013@163.com
 * Date：2018/3/20.
 */

public class HttpCacheInterceptor implements Interceptor {
    //  配置缓存的拦截器
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String cacheControl = request.cacheControl().toString();
        if (!NetworkUtils.isConnected(BaseApplication.getInstance())) {//没网从缓存读取
            request = request.newBuilder()
                    .cacheControl(TextUtils.isEmpty(cacheControl) ? CacheControl.FORCE_CACHE : CacheControl.FORCE_NETWORK)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (NetworkUtils.isConnected(BaseApplication.getInstance())) {//有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            //有网的时候连接服务器请求,缓存一天
            int MAX_AGE = 60 * 60 * 24;
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + MAX_AGE)
                    .removeHeader("Pragma")
                    .build();
        } else {
            //网络断开时读取缓存4周
            int CACHE_STALE_SEC = 60 * 60 * 24 * 28;
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
                    .removeHeader("Pragma")
                    .build();
        }
    }
}