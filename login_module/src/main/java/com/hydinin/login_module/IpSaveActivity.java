package com.hydinin.login_module;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hydinin.base_module.base.BaseActivity;
import com.hydinin.base_module.constant.Constant;
import com.hydinin.base_module.utils.SPUtil;
import com.hydinin.base_module.utils.ToastUtil;
import com.hydinin.base_module.utils.UIHelper;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.HttpUrl;

public class IpSaveActivity extends BaseActivity {

    @BindView(R2.id.ipEdit)
    EditText ipEdit;
    @BindView(R2.id.portEdit)
    EditText portEdit;
    @BindView(R2.id.saveIP)
    TextView saveIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String ip = (String) SPUtil.get(this, Constant.IpInfo.IP, "");
        String port = (String) SPUtil.get(this, Constant.IpInfo.PORT, "");
        ipEdit.setText(ip);
        portEdit.setText(port);
    }

    @OnClick({R2.id.saveIP})
    void setOnClick(View view) {
        int id = view.getId();
        if (id == R.id.saveIP) {
            String ip = ipEdit.getText().toString().trim();
            String port = portEdit.getText().toString().trim();
            String url="http://"+ip+":"+port+"/portal/";
            HttpUrl httpUrl = HttpUrl.parse(url);
            if (httpUrl == null) {
                ToastUtil.show(this.getApplicationContext(),"您输入的ip或端口无效");
            }else {
                SPUtil.putAndApply(this, Constant.IpInfo.IP, ip);
                SPUtil.putAndApply(this, Constant.IpInfo.PORT, port);
                Toast.makeText(this,"保存成功",Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_ip_save;
    }
}
