package com.hydinin.login_module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hydinin.base_module.api.HttpResult;
import com.hydinin.base_module.arouter.ARouteConstant;
import com.hydinin.base_module.base.MVPActivity;
import com.hydinin.base_module.beanJson.User;
import com.hydinin.base_module.constant.Constant;
import com.hydinin.base_module.utils.CommonUtils;
import com.hydinin.base_module.utils.SPUtil;
import com.hydinin.base_module.utils.ToastUtil;
import com.hydinin.base_module.utils.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;


@Route(path = ARouteConstant.Login_Activity_login)
public class LoginActivity extends MVPActivity<LoginPersent, LoginModel> implements LoginContract.LoginView {


    @BindView(R2.id.login_user)
    EditText loginUser;
    @BindView(R2.id.login_pwd)
    EditText loginPwd;
    @BindView(R2.id.login_record)
    CheckBox loginRecord;
    private String userName;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        Boolean isRecord = (Boolean) SPUtil.get(this, Constant.ISRECORD, false);
        String name = (String) SPUtil.get(this, Constant.LoginInfo.LOGINNAME, "");
        String pwd = (String) SPUtil.get(this, Constant.LoginInfo.LOGINPWD, "");
        if (isRecord){
            loginUser.setText(name);
            loginPwd.setText(pwd);
            loginRecord.setChecked(true);
        }else {
            loginUser.setText(name);
            loginRecord.setChecked(false);
        }
        String ip = (String) SPUtil.get(this, Constant.IpInfo.IP, "");
        String port = (String) SPUtil.get(this, Constant.IpInfo.PORT, "");
        if (!CommonUtils.isEmpty(ip) && !CommonUtils.isEmpty(port)) {
           //getVersion();
        } else {
            SPUtil.putAndApply(this, Constant.IpInfo.IP, Constant.SERVERIP);
            SPUtil.putAndApply(this, Constant.IpInfo.PORT, Constant.SERVERPORT);
        }
    }

    @OnClick({R2.id.login_login,R2.id.login_ip})
    void setOnClick(View view){
        int id = view.getId();
        if (id==R.id.login_login){
            userName = loginUser.getText().toString().trim();
            pwd = loginPwd.getText().toString().trim();
            String ip = (String) SPUtil.get(this, Constant.IpInfo.IP, "");
            String port = (String) SPUtil.get(this, Constant.IpInfo.PORT, "");
            if (!CommonUtils.isEmpty(ip) && !CommonUtils.isEmpty(port)) {
                if (!CommonUtils.isEmpty(userName) && !CommonUtils.isEmpty(pwd)) {
                   mPresenter.loginUser(this,userName, pwd,this.<HttpResult<User>>bindToLifecycle());
                } else {
                    Toast.makeText(this, "用户名密码不能为空", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请去ip页面输入ip和端口", Toast.LENGTH_SHORT).show();
            }
        }else if (id==R.id.login_ip){
            UIHelper.openActivity(this,IpSaveActivity.class);
        }
    }


    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void showUserDate(User user) {
        SPUtil.putAndApply(LoginActivity.this,Constant.ISRECORD,loginRecord.isChecked());
        if (loginRecord.isChecked()){
            SPUtil.putAndApply(LoginActivity.this, Constant.LoginInfo.LOGINNAME,userName);
            SPUtil.putAndApply(LoginActivity.this, Constant.LoginInfo.LOGINPWD, pwd);
        }else {
            SPUtil.putAndApply(LoginActivity.this, Constant.LoginInfo.LOGINNAME,userName);
        }
        //SPUtil.putAndApply(this, Constant.USERID,user.getId()+"");
      //  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
      //  intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      //  startActivity(intent);
        ARouteConstant.startMainActivity(user);
        this.finish();
    }

    @Override
    public void showError(String message) {
        ToastUtil.show(getApplicationContext(),message);
        User user=new User();
        ARouteConstant.startMainActivity(user);
    }
}
