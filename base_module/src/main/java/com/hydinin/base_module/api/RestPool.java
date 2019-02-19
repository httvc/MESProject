package com.hydinin.base_module.api;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hydinin.base_module.BaseApplication;
import com.hydinin.base_module.constant.Constant;
import com.hydinin.base_module.utils.SPUtil;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android Studio
 * Project：ZhiChuang
 * Author：httvc
 * Email：jfjie2013@163.com
 * Date：2017/5/5.
 * 接口解析封装类
 */

public class RestPool {
    private volatile static RestPool restPool;
    private Retrofit retrofit;

    private RestPool() {
        String ip = (String) SPUtil.get(BaseApplication.getInstance().getApplicationContext(), Constant.IpInfo.IP, "");
        String port = (String) SPUtil.get(BaseApplication.getInstance().getApplicationContext(), Constant.IpInfo.PORT, "");
         String url="http://"+ip+":"+port+"/portal/";
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // log拦截器  打印所有的log
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(BaseApplication.getInstance().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);//100M
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(40, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS).writeTimeout(5, TimeUnit.SECONDS) //设置超时
                .addInterceptor(logging)
                .addInterceptor(new HttpHeaderInterceptor())
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .addNetworkInterceptor(new StethoInterceptor())
                // .sslSocketFactory(SslContextFactory.getSSLSocketFactoryForTwoWay())   https认证 如果要使用https且为自定义证书 可以去掉这两行注释，并自行配制证书。
                // .hostnameVerifier(new SafeHostnameVerifier())
                .cache(cache)
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        retrofit = new Retrofit.Builder()///192.168.1.219:8080
                .client(client)//"http://"+ip+":"+port+"/portal/
                .baseUrl(url)//192.168.1.186:8080
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static RestPool getInstance() {
        if (restPool == null) {
            synchronized (RestPool.class) {
                if (restPool == null) {
                    restPool = new RestPool();
                }
            }
        }
        return restPool;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

    /**
     * 文件上传body
     *
     * @param key
     * @param mediaType
     * @param file
     * @return
     */
    public static MultipartBody.Part getMultiPart(String key, String mediaType, File file) {
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, file.getName(),
                RequestBody.create(MediaType.parse(mediaType), file));
        return body;
    }

    public static RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"), value);
    }

    public static MultipartBody.Builder getMultiBuilder(HashMap<String, File> map) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        Set<String> set = map.keySet();
        for (String key : set) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), map.get(key));
            builder.addFormDataPart(key, map.get(key).getName(), requestFile);
        }
        return builder;
    }

  /*  private class SafeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            if (Constants.IP.equals(hostname)) {//校验hostname是否正确，如果正确则建立连接
                return true;
            }
            return false;
        }
    }*/
}
