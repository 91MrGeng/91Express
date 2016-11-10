package com.cea.celibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetStateUtils {
    public static final int NETWORN_NONE = 0; //网络错误
    public static final int NETWORN_WIFI = 1;//wifi连接
    public static final int NETWORN_MOBILE = 2;//3G格式连接
    /**
     * 获取手机网络状态服务
     * @param context
     * @return
     */
    public static int getNetworkState(Context context) {
        //获取网络状态服务管理对象
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // Wifi
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NETWORN_WIFI;
        }

        // 3G
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NETWORN_MOBILE;
        }
        return NETWORN_WIFI;
    }
}
