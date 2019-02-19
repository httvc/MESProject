package com.hydinin.login_module;

import android.content.Context;

import com.hydinin.base_module.api.HttpResult;
import com.hydinin.base_module.base.BaseModel;
import com.hydinin.base_module.base.BasePresent;
import com.hydinin.base_module.base.BaseView;
import com.hydinin.base_module.beanJson.User;
import com.hydinin.base_module.listener.MVPListener;
import com.trello.rxlifecycle2.LifecycleTransformer;

public interface LoginContract {
    interface LoginView extends BaseView {
        void showUserDate(User user);
    }
    interface LoginModel extends BaseModel {
        void loginUser(Context context,String username, String pwd, LifecycleTransformer<HttpResult<User>> bindToLifecycle, MVPListener pMVPListener);
    }

    abstract class LoginPresent extends BasePresent<LoginModel,LoginView> {
        public abstract void loginUser(Context context,String username, String pwd,LifecycleTransformer<HttpResult<User>> bindToLifecycle);
    }
}
