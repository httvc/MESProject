package com.hydinin.mesproject;


import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.hydinin.base_module.arouter.ARouteConstant;
import com.hydinin.base_module.base.BaseActivity;
import com.hydinin.base_module.utils.UIHelper;
import com.hydinin.login_module.LoginActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions();
       // ARouteConstant.StartLoginActvity();
        UIHelper.openActivity(this,LoginActivity.class);
        finish();
    }

    /**
     * 权限请求
     */
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .request(Manifest.permission.CAMERA)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            //获得了所有权限
                           // textView.setAnimation(animationSet);
                        } else {
                            //至少有一个权限没有获得
                            Toast.makeText(MyApplication.getInstance().getApplicationContext(), "请给应用赋予全部权限，否则无法使用", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
