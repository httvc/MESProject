package com.hydinin.base_module.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hydinin.base_module.R;
import com.hydinin.base_module.utils.StatusBarUtil;
import com.hydinin.base_module.widget.TitleView;

import butterknife.ButterKnife;

public abstract class BaseActivity extends RxBaseActivity {
    private ProgressDialog mProgressDialog;
    private TitleView titleView;
    public ViewGroup mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 方向锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mView = (ViewGroup) LayoutInflater.from(this).inflate(getLayout(), null);
        StatusBarUtil.translucentStatusBar(this);
        setContentView(mView);
        ButterKnife.bind(this);
    }

    public abstract int getLayout();

    /**
     * 标题导航栏
     * @return
     */
    public TitleView getTitleView() {
        return titleView == null ? new TitleView(this, (View) findViewById(R.id.header)) : titleView;
    }
}
