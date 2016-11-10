package com.e91.express.api.impl;


import com.e91.express.api.BaseApiService;
import com.e91.express.api.RegisterApi;
import com.e91.express.model.pojo.register.RegisterBody;

import retrofit.http.Field;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author devin
 * @Class RegisterApiService
 * @Date 16/7/3
 */
public class RegisterApiService extends BaseApiService implements RegisterApi.RegisterService {
    @Override
    public Observable<RegisterBody> register(@Field("username") String username, @Field("password") String password, @Field("mobile") String mobile) {
        return getApi().register(username,password,mobile).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
    }
}
