package com.hydinin.base_module.api;


import com.hydinin.base_module.beanJson.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Android Studio
 * Author：httvc
 * Email：jfjie2013@163.com
 * Date：2018/3/20.
 */

public interface ApiService {

    /**
     * 登录接口
     * @param
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("conservationmobile/loginUser")
    Observable<HttpResult<User>> login(@Field("username") String userName, @Field("password") String password);

}
