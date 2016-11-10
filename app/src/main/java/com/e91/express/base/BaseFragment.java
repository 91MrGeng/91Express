package com.e91.express.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cea.celibrary.customview.ProgressDialog;

import butterknife.ButterKnife;
import icepick.Icepick;
import nucleus.presenter.Presenter;
import nucleus.view.NucleusSupportFragment;
import rx.subscriptions.CompositeSubscription;

/**
 * @author devin
 * @Class BaseFragment
 * @Date 16/5/1
 */
public abstract class BaseFragment<PresenterType extends Presenter> extends NucleusSupportFragment<PresenterType> {


    protected View mRootView;
    protected CompositeSubscription subscription = new CompositeSubscription();

    @Override
    public void onCreate(Bundle bundle) {

        super.onCreate(bundle);
        injectPresenter();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        return mRootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
//        BusProvider.getInstance().register(this);
        //由上往下初始化
        initToolBar();
        initTabs();
        initEvent();

        afterCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        BusProvider.getInstance().unregister(this);
        ProgressDialog.destoryDialog();
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        Icepick.saveInstanceState(this, bundle);
    }

    public BaseFragment() {
    }

    protected void injectPresenter() {

    }

    protected void initBase() {
    }

    protected void initToolBar() {
    }

    protected void initTabs() {
    }

    protected abstract int getLayoutId();

    protected abstract void afterCreate(Bundle savedInstanceState);

    protected void initEvent() {
    }

    protected void initObserver() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}