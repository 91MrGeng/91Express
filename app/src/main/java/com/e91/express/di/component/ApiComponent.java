package com.e91.express.di.component;


import com.e91.express.api.BaseApiService;
import com.e91.express.di.module.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author devin
 * @Class ApiComponent
 * @Date 16/5/1
 */
@Singleton
@Component(modules = {ApiModule.class})
public interface ApiComponent {

    void inject(BaseApiService baseApiService);

    class Instance {
        private static ApiComponent apiComponent;

        public static void init(ApiComponent component) {
            apiComponent = component;
        }

        public static ApiComponent get() {
            return apiComponent;
        }
    }
}
