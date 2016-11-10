package com.e91.express.ui.fragment;

import android.app.Activity;
import android.os.Bundle;

import com.e91.express.R;
import com.e91.express.base.BaseFragment;
import com.e91.express.mvp.presenter.HomePresenter;
import com.e91.express.mvp.view.HomeView;

/**
 * =====================================
 * 项目名称：com.e91.express.ui.fragment
 * 类描述：
 * 创建人：大风车
 * 创建时间：2016/10/23 15:39
 * 修改人：大风车
 * 修改时间：2016/10/23 15:39
 * 版本说明：Version1.0
 * =====================================
 */

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView {

    private Activity mContext;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {

    }


    @Override
    public void showErrMsg(String errMsg, Object o) {

    }

    @Override
    public void showProgress(String pgMsg, Object o) {

    }

    @Override
    public void showSuccessMsg(String successMsg, Object o) {

    }





}
