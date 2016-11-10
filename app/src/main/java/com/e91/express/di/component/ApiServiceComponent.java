package com.e91.express.di.component;


import com.e91.express.di.module.ApiServiceModule;
import com.e91.express.mvp.presenter.RegisterPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author devin
 * @Class ApiServiceComponent
 * @Date 16/5/1
 */
@Singleton
@Component(modules = ApiServiceModule.class)
public interface ApiServiceComponent {

//    void inject(HomePresenter homePresenter);
//
//    void inject(LoginPresenter loginPresenter);

      void inject(RegisterPresenter registerPresenter);

    class Instance {
        private static ApiServiceComponent apiServiceComponent;

        public static void init(ApiServiceComponent component) {
            apiServiceComponent = component;
        }

        public static ApiServiceComponent get() {
            return apiServiceComponent;
        }
    }
}
