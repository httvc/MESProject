package com.hydinin.user_module.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hydinin.base_module.base.BaseFragment;
import com.hydinin.user_module.R;


public class MeFragment extends BaseFragment {


    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_me;
    }

}
