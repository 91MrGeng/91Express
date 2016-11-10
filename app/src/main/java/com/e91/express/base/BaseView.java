package com.e91.express.base;

/**
 * @author devin
 * @Class BaseView
 * @Date 16/5/1
 */
public interface BaseView {
    void showErrMsg(String errMsg, Object o);

    void showProgress(String pgMsg, Object o);

    void showSuccessMsg(String successMsg, Object o);
}
