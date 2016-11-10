package com.e91.express.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.cea.celibrary.customview.ProgressDialog;
import com.e91.express.common.ActivityCollector;

import butterknife.ButterKnife;
import icepick.Icepick;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * @author devin
 * @Class BaseActivity
 * @Date 16/5/1
 */
public abstract class BaseActivity<PresenterType extends Presenter> extends NucleusAppCompatActivity<PresenterType> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectPresenter();
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        Icepick.restoreInstanceState(this, savedInstanceState);
        ActivityCollector.addActivity(this);
        afterCreate(savedInstanceState);
    }

    protected void injectPresenter() {

    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        ProgressDialog.destoryDialog();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }
    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    //重写,fragment才能触发onActivityResult()
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    //重写,fragment才能触发onRequestPermissionsResult()
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case android.R.id.home:
//                finish();
//        }
        return super.onOptionsItemSelected(item);
    }
}
