package com.cea.celibrary.customview;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ProgressDialog {

    private static ProgressDialog instance;
    private static Context mContext;

    private SweetAlertDialog dialog;

    public static ProgressDialog getInstance(Context context) {
        if (instance == null) {
            synchronized (ProgressDialog.class) {
                if (instance == null) {
                    instance = new ProgressDialog(context, SweetAlertDialog.PROGRESS_TYPE);
                }
            }

        }
        return instance;
    }

    public ProgressDialog(Context context, int type) {
        mContext = context;
        dialog = new SweetAlertDialog(context, type);
    }

    public SweetAlertDialog getDialog() {
        return dialog;
    }
    public SweetAlertDialog getDialog(int type) {
        dialog.changeAlertType(type);
        return dialog;
    }
    public static void destoryDialog() {
        instance = null;
    }
}
