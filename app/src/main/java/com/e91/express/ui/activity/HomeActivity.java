package com.e91.express.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.e91.express.R;
import com.e91.express.base.BaseActivity;
import com.e91.express.ui.fragment.HomeFragment;


/**
 * =====================================
 * 项目名称：com.e91.express.ui.activity
 * 类描述：主界面
 * 创建人：大风车
 * 创建时间：2016/10/23 15:37
 * 修改人：大风车
 * 修改时间：2016/10/23 15:37
 * 版本说明：Version1.0
 * =====================================
 */

public class HomeActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.main_container;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        HomeFragment homeFragment = HomeFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_container,homeFragment);
        ft.commit();
    }


}
