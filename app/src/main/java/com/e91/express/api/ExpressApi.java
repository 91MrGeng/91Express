package com.e91.express.api;


import com.e91.express.model.pojo.register.RegisterBody;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * @author devin
 * @Class CityExpressApi
 * @Date 16/5/1
 */
public interface ExpressApi {

    /**
     * 注册
     * @param username
     * @param password
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST(RegisterApi.URL.REGISTER_URL)
    Observable<RegisterBody> register(@Field("username") String username,
                                      @Field("password") String password,
                                      @Field("mobile") String mobile);

}
