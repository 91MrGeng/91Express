package com.e91.express.mvp.presenter;

import android.os.Bundle;

import com.cea.celibrary.utils.L;
import com.e91.express.api.RegisterApi;
import com.e91.express.base.BasePresenter;
import com.e91.express.model.pojo.register.RegisterBody;
import com.e91.express.mvp.view.RegisterView;
import com.e91.express.network.HttpExceptionUtils;
import com.e91.express.network.ResultException;

import javax.inject.Inject;

import icepick.State;
import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;


/**
 * @author devin
 * @Class RegisterPresenter
 * @Date 16/7/3
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {

    @Inject
    RegisterApi registerApi;

    @State
    String username,password,mobile;

    public static final int REGISTER = 1;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableFirst(REGISTER, new Func0<Observable<RegisterBody>>() {
            @Override
            public Observable<RegisterBody> call() {
                return registerApi.getRegisterSerevice().register(username,password,mobile);
            }
        }, new Action2<RegisterView, RegisterBody>() {
            @Override
            public void call(RegisterView registerView, RegisterBody bodyBaseResponse) {
                L.i("成功");
                registerView.showSuccessMsg(bodyBaseResponse.getInfo(),null);
            }
        }, new Action2<RegisterView, Throwable>() {
            @Override
            public void call(RegisterView registerView, Throwable throwable) {
                L.i("失败"+throwable);
                if (throwable instanceof ResultException)
                registerView.showErrMsg(HttpExceptionUtils.getResultException(throwable).getErrorMessage(),null);
            }
        });
    }

    public void register(String username,String password,String mobile){
        this.username = username;
        this.password = password;
        this.mobile = mobile;
        start(REGISTER);
    }
}
