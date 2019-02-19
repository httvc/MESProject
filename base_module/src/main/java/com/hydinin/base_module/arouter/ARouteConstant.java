package com.hydinin.base_module.arouter;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hydinin.base_module.beanJson.User;

public class ARouteConstant {
    public static final String Login_Activity_login="/login/login";
    public static final String app_activity_Main="/app/main";


    public static void StartLoginActvity(){
        ARouter.getInstance().build(Login_Activity_login).navigation();
    }

    public static void startMainActivity(User user){
        ARouter.getInstance().build(app_activity_Main).withSerializable("user",user).navigation();
    }
}
