package com.e91.express.api;


import com.e91.express.model.pojo.register.RegisterBody;

import retrofit.http.Field;
import rx.Observable;

/**
 * @author devin
 * @Class RegisterApi
 * @Date 16/7/3
 */
public class RegisterApi {
    public static class URL{
        public static final String REGISTER_URL="register/index";
    }

    private RegisterService registerService;

    public RegisterApi(RegisterService registerService){
        this.registerService = registerService;
    }

    public RegisterService getRegisterSerevice(){
        return registerService;
    }


    public interface RegisterService{
        Observable<RegisterBody> register(@Field("username") String username,
                                          @Field("password") String password,
                                          @Field("mobile") String mobile);
    }
}
