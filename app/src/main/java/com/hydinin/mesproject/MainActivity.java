package com.hydinin.mesproject;

import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hydinin.base_module.arouter.ARouteConstant;
import com.hydinin.base_module.base.BaseActivity;
import com.hydinin.base_module.utils.StatusBarUtil;
import com.hydinin.mesproject.widget.HomeGenerator;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

@Route(path = ARouteConstant.app_activity_Main)
public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_tab_layout)
    TabLayout mTabLayout;
    private Fragment[] mFragmensts;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTabLayout();
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        // Example of a call to a native method

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    private void onTabItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mFragmensts[0];
                break;
            case 1:
                fragment = mFragmensts[1];
                break;
            case 2:
                fragment = mFragmensts[2];
                break;
        }
        for (int i = 0; i < mFragmensts.length; i++) {
            getSupportFragmentManager().beginTransaction().hide(mFragmensts[i]).commit();
        }
        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.home_container, fragment).commit();
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        }
    }

    private void initTabLayout() {
        mFragmensts = HomeGenerator.getFragments();
        for (int i = 0; i < mFragmensts.length; i++) {
            getSupportFragmentManager().beginTransaction().add(R.id.home_container, mFragmensts[i]).commit();
        }
        getSupportFragmentManager().beginTransaction().show(mFragmensts[0]).commit();
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                onTabItemSelected(tab.getPosition());
                // Tab 选中之后，改变各个Tab的状态
                for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                    View view = mTabLayout.getTabAt(i).getCustomView();
                    ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image1);
                    TextView text = (TextView) view.findViewById(R.id.tab_content_text);
                    if (i == tab.getPosition()) { // 选中状态
                       // icon.setImageResource(HomeGenerator.mTabResPressed[i]);
                       // text.setTextColor(getResources().getColor(R.color.green));
                        icon.setVisibility(View.VISIBLE);
                    } else {// 未选中状态
                        icon.setVisibility(View.INVISIBLE);
                       // icon.setImageResource(HomeGenerator.mTabRes[i]);
                       // text.setTextColor(getResources().getColor(R.color.color_171717));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // 提供自定义的布局添加Tab
        for (int i = 0; i < 3; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(HomeGenerator.getTabView(this, i)));
        }
    }

}
