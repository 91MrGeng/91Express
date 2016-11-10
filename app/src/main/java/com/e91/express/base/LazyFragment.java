package com.e91.express.base;

import nucleus.presenter.Presenter;

/**
 * @author devin
 * @Class LazyFragment
 * @Date 16/5/1
 */
public abstract class LazyFragment<PreserentType extends Presenter> extends BaseFragment<PreserentType> {

    public boolean isVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected abstract void lazyLoad();
}
