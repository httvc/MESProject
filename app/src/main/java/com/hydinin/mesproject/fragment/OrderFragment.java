package com.hydinin.mesproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hydinin.base_module.base.BaseFragment;
import com.hydinin.mesproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends BaseFragment {


    public OrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_order;
    }

}
