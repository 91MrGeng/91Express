package com.e91.express.di.module;

import android.content.Context;

import com.e91.express.AppApplication;
import com.e91.express.di.qualifier.AppContext;
import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author devin
 * @Class AppModule
 * @Date 16/5/1
 */
@Module
public class AppModule {
    private final AppApplication appApplication;

    public AppModule(AppApplication appApplication) {
        this.appApplication = appApplication;
    }


    @Provides
    @Singleton
    @AppContext
    public Context provideAppContext() {
        return appApplication;
    }


    @Provides
    @Singleton
    public Prefser providePrefser(){
        return new Prefser(appApplication);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder().serializeNulls().create();
    }

}
