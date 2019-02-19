package com.hydinin.login_module;

import android.content.Context;

import com.hydinin.base_module.api.HttpResult;
import com.hydinin.base_module.beanJson.User;
import com.hydinin.base_module.constant.Constant;
import com.hydinin.base_module.listener.MVPListener;
import com.trello.rxlifecycle2.LifecycleTransformer;

public class LoginPersent extends LoginContract.LoginPresent {

    @Override
    public void loginUser(Context context,String username, String pwd, LifecycleTransformer<HttpResult<User>> bindToLifecycle) {
        mModel.loginUser(context,username, pwd,bindToLifecycle, new MVPListener<User>() {
            @Override
            public void onSuccess(User result) {
                mView.showUserDate(result);
            }

            @Override
            public void onFail(String msg) {
                mView.showError(msg);
            }
        });
    }

}
