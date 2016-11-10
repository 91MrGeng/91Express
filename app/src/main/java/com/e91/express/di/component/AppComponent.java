package com.e91.express.di.component;


import com.e91.express.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author devin
 * @Class AppComponent
 * @Date 16/5/1
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    class Instance {
        private static AppComponent appComponent;

        public static void init(AppComponent component) {
            appComponent = component;
        }

        public static AppComponent get() {
            return appComponent;
        }
    }
}