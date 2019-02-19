package com.hydinin.login_module;

import android.content.Context;

import com.hydinin.base_module.BaseApplication;
import com.hydinin.base_module.api.HttpResult;
import com.hydinin.base_module.api.MyObserver;
import com.hydinin.base_module.api.RestPool;
import com.hydinin.base_module.beanJson.User;
import com.hydinin.base_module.listener.MVPListener;
import com.trello.rxlifecycle2.LifecycleTransformer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel implements LoginContract.LoginModel {
    @Override
    public void loginUser(Context context,String username, String pwd, LifecycleTransformer<HttpResult<User>> bindToLifecycle, final MVPListener pMVPListener) {
        RestPool.getInstance().getApiService().login(username, pwd)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle)
                .subscribe(new MyObserver<HttpResult<User>>(context,"登录中...") {
                    @Override
                    public void onSuccess(HttpResult<User> userHttpResult) {
                        User result = userHttpResult.getResult();
                        pMVPListener.onSuccess(result);
                    }

                    @Override
                    public void onFailure(boolean success, String message) {
                        pMVPListener.onFail(message);
                    }
                });
    }
}
