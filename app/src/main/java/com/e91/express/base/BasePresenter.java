package com.e91.express.base;

import android.os.Bundle;

import icepick.Icepick;
import nucleus.presenter.RxPresenter;

/**
 * @author devin
 * @Class HomePresenter
 * @Date 16/5/1
 */
public abstract class BasePresenter<View> extends RxPresenter<View> {
    public String errMsg = "请检查网络设置";

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        Icepick.restoreInstanceState(this, savedState);
    }

    @Override
    protected void onSave(Bundle state) {
        super.onSave(state);
        Icepick.saveInstanceState(this, state);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}