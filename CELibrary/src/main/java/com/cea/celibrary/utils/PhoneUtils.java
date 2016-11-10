package com.cea.celibrary.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;


public class PhoneUtils {
    /**
     *
     * @return 手机型号
     */
    public static String getModel() {

        return Build.MODEL;
    }

    /**
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     *
     * @param context 上下文对象
     * @return 网络类型
     */
    public static String getNetState(Context context) {
        switch (NetStateUtils.getNetworkState(context)) {
            case NetStateUtils.NETWORN_MOBILE:

                return "mobile";

            case NetStateUtils.NETWORN_NONE:

                return "none";
            case NetStateUtils.NETWORN_WIFI:

                return "wifi";
            default:
                break;
        }
        return "fail";
    }

    /**
     *
     * @param context 上下文对象
     * @return 版本名称
     */
    public static String getVersionName(Context context){
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "faile";
    }

    /**
     *
     * @param context 上下文对象
     * @return 版本号
     */
    public static int getVersionCode(Context context){
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     *
     * @param context 上下文对象
     * @return 渠道号
     */
    public static String getChannel(Context context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getString("InstallChannel");
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "fail";
    }
}
