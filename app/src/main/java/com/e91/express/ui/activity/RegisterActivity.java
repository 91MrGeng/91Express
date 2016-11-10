package com.e91.express.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.e91.express.R;
import com.e91.express.base.BaseActivity;
import com.e91.express.ui.fragment.RegisterFragment;


/**
 * @author devin
 * @Class RegisterActivity
 * @Date 16/7/3
 */
public class RegisterActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.main_container;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_container,registerFragment);
        ft.commit();
    }
}
