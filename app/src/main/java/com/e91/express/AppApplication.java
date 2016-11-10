package com.e91.express;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.e91.express.common.CEConstants;
import com.e91.express.di.component.ApiComponent;
import com.e91.express.di.component.ApiServiceComponent;
import com.e91.express.di.component.AppComponent;
import com.e91.express.di.component.DaggerApiComponent;
import com.e91.express.di.component.DaggerApiServiceComponent;
import com.e91.express.di.component.DaggerAppComponent;
import com.e91.express.di.module.AppModule;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class AppApplication extends Application{
    private RefWatcher refWatcher;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent.Instance.init(DaggerAppComponent.builder().appModule(new AppModule(this)).build());
        ApiComponent.Instance.init(DaggerApiComponent.builder().appModule(new AppModule(this)).build());
        ApiServiceComponent.Instance.init(DaggerApiServiceComponent.builder().appModule(new AppModule(this)).build());

        /**
         * LeakCanary
         */


        refWatcher = LeakCanary.install(this);

        /**
         * Logger
         */
        Logger.init(CEConstants.Tools.TAG);
    }

    public static RefWatcher getRefWatcher(Context context) {
        AppApplication application = (AppApplication) context
                .getApplicationContext();
        return application.refWatcher;
    }


    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
