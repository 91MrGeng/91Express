package com.cea.celibrary.utils;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Devin on 2016/2/29.
 */
public class AMapUtils {

    private static AMapLocationClient mapLocationClient;

    private static AMapLocationClientOption mapLocationClientOption;

    private static String city;

    private static String cityCode;

    private static String address;

    private static int errorCode = 0;

    private static String errorString = "";

    private static String province;

    private AMapUtils() {

    }

    public static AMapLocationListener mapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            setInfo(aMapLocation);
        }
    };

    public static String getProvince() {
        if (errorCode == 0 && province != null)
            return province;
        return null;
    }

    public static String getProvince2() {
        if (errorCode == 0 && province != null)
            return province.substring(0, 2);
        return null;
    }

    public static String getCity() {
        if (errorCode == 0 && city != null)
            return city;
        return null;
    }

    public static String getCity2() {
        if (errorCode == 0 && city != null)
            return city.substring(0, 2);
        return null;
    }

    public static String getCityCode(){
        if (errorCode == 0 && cityCode != null){
            return cityCode;
        }
        return null;
    }

    public static String getAddress() {
        if (errorCode == 0 && address != null)
            return address;
        return null;
    }

    public static int getErrorCode() {
        return errorCode;
    }

    public static String getErrorString() {
        return errorString;
    }


    public static void emptyData() {
        errorCode = 0;
        errorString = "";
        address = "";
        city = "";
    }

    private static void setInfo(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            setMapInfo(aMapLocation);
        }
    }

    private static void setMapInfo(AMapLocation aMapLocation) {
        if (aMapLocation.getErrorCode() == 0) {
            setCorrectInfo(aMapLocation);
        } else {
            setError(aMapLocation);
        }
    }

    private static void setCorrectInfo(AMapLocation aMapLocation) {
        city = aMapLocation.getCity();
        cityCode = aMapLocation.getCityCode();
        address = aMapLocation.getAddress();
        province = aMapLocation.getProvince();
    }

    private static void setError(AMapLocation aMapLocation) {
        errorCode = aMapLocation.getErrorCode();
        errorString = aMapLocation.getErrorInfo();
        Log.i("errorCode", errorCode + "");
        Log.i("errorString", errorString);
    }

    /**
     * 初始化
     *
     * @param context
     */
    public static AMapLocationClient initAmapLocation(Context context) {
        initMapLocationClientOption();
        initMapLocationClient(context);
        return mapLocationClient;
    }

    /**
     * 初始化 Client
     *
     * @param context
     */
    private static void initMapLocationClient(Context context) {
        mapLocationClient = new AMapLocationClient(context);
        mapLocationClient.setLocationListener(mapLocationListener);
        mapLocationClient.setLocationOption(mapLocationClientOption);
    }

    /**
     * 初始化clientOption
     */
    private static void initMapLocationClientOption() {
        mapLocationClientOption = new AMapLocationClientOption();

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //设置是否返回地址信息（默认返回地址信息）
        mapLocationClientOption.setNeedAddress(true);

        //设置是否只定位一次,默认为false
        mapLocationClientOption.setOnceLocation(false);

        //设置是否强制刷新WIFI，默认为强制刷新
        mapLocationClientOption.setWifiActiveScan(true);

        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mapLocationClientOption.setMockEnable(false);

        //设置定位间隔,单位毫秒,默认为2000ms
        mapLocationClientOption.setInterval(30 * 60 * 1000);//半小时

    }

    /**
     * start定位服务
     */
    public static void startLocation() {
        if (!isStartedLocation()) {
            mapLocationClient.startLocation();
        }
    }

    /**
     * 停止定位服务
     */
    public static void stopLocation() {
        if (isStartedLocation())
            mapLocationClient.stopLocation();
    }

    /**
     * 是否开始定位服务
     *
     * @return boolean
     */
    public static boolean isStartedLocation() {
        return mapLocationClient != null && mapLocationClient.isStarted();
    }


    /**
     * 销毁定位服务
     */
    public static void destoryLocation() {
        stopLocation();
        if (mapLocationClient != null) {
            mapLocationClient.onDestroy();
        }
    }
}
