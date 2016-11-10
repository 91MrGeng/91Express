package com.e91.express.api;

import com.e91.express.di.component.ApiComponent;
import com.google.gson.Gson;

import javax.inject.Inject;


/**
 * @author Niger
 * @Class BaseApiService
 * @Date 16/10/20
 */
public class BaseApiService {
    @Inject
    ExpressApi experssApi;

    @Inject
    Gson gson;

    public BaseApiService() {
        ApiComponent.Instance.get().inject(this);
    }

    /**
     * 获取指定Api接口对象
     *
     * @return
     */
    public ExpressApi getApi() {
        return experssApi;
    }

    /**
     * 获取Gson对象
     *
     * @return
     */
    public Gson getGson() {
        return gson;
    }
}