package com.hydinin.mesproject;

import android.os.Bundle;
import android.app.Activity;

import com.hydinin.base_module.base.BaseActivity;

public class DetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

}
