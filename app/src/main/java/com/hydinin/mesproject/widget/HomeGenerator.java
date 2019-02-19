package com.hydinin.mesproject.widget;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hydinin.mesproject.MyApplication;
import com.hydinin.mesproject.R;
import com.hydinin.mesproject.fragment.HomeFragment;
import com.hydinin.mesproject.fragment.OrderFragment;
import com.hydinin.user_module.fragment.MeFragment;

public class HomeGenerator {
   /* public static final int[] mTabRes =
            new int[]{R.mipmap.icon_home, R.mipmap.icon_order, R.mipmap.icon_me};*/

    public static final int[] mTabResPressed = new int[]{R.mipmap.icon_home, R.mipmap.icon_order, R.mipmap.icon_me};
    public static final String[] mTabTitle = new String[]{"首页","订单","我的"};

    public static Fragment[] getFragments() {
        Fragment fragments[] = new Fragment[3];
        fragments[0] = new HomeFragment();
        fragments[1] = new OrderFragment();
        fragments[2] = new MeFragment();
        return fragments;
    }

    /**
     * 获取Tab 显示的内容
     *
     * @param context
     * @param position
     * @return
     */
    public static View getTabView(Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_tab_content, null);
        ImageView tabIcon1=(ImageView)view.findViewById(R.id.tab_content_image1);
        ImageView tabIcon = (ImageView) view.findViewById(R.id.tab_content_image);
        tabIcon.setImageResource(HomeGenerator.mTabResPressed[position]);
        TextView tabText = (TextView) view.findViewById(R.id.tab_content_text);
        tabText.setText(mTabTitle[position]);
        return view;
    }
}
