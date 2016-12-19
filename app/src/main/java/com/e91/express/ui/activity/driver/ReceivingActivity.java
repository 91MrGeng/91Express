package com.e91.express.ui.activity.driver;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cea.celibrary.utils.L;
import com.e91.express.R;
import com.e91.express.base.BaseActivity;
import com.e91.express.ui.fragment.driver.ReceivingFragment;

import butterknife.Bind;
import butterknife.OnClick;


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

public class ReceivingActivity extends BaseActivity {
    @Bind(R.id.toolbarBase)
    Toolbar toolbar;
    @Bind(R.id.toolbar_title)
    TextView toolbar_title;
    @Bind(R.id.back)
    ImageView back;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_container;
    }

    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        setToolBar();
        setFragment();

    }
    /**
     * 设置Title
     */
    private void setToolBar() {
        getWindow().setBackgroundDrawableResource(R.color.background_material_light_1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbar_title.setText("接单");
        back.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.back)
    public void back() {
        L.i("onclick");
        this.finish();
    }
    private void setFragment() {
        ReceivingFragment receivingFragment = ReceivingFragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.al_container,receivingFragment);
        ft.commit();
    }


}
