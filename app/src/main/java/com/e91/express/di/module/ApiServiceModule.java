package com.e91.express.di.module;


import com.e91.express.api.RegisterApi;
import com.e91.express.api.impl.RegisterApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author devin
 * @Class ApiServiceModule
 * @Date 16/5/1
 */

@Module(includes = AppModule.class)
public class ApiServiceModule {
    @Singleton
    @Provides
    public RegisterApi provideRegisterApi(){
        return new RegisterApi(new RegisterApiService());
    }
}
