package com.cea.celibrary.utils;

import com.orhanobut.logger.Logger;

public class L {
    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化

    public static void i(int msg) {
        i(String.valueOf(msg));
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Logger.i(msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Logger.d(msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Logger.e(msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Logger.v(msg);
    }

    //下面是传入类名打印log
    public static void i(Class<?> _class, String msg) {
        if (isDebug)
            Logger.i(_class.getName(), msg);
    }

    public static void d(Class<?> _class, String msg) {
        if (isDebug)
            Logger.i(_class.getName(), msg);
    }

    public static void e(Class<?> _class, String msg) {
        if (isDebug)
            Logger.i(_class.getName(), msg);
    }

    public static void v(Class<?> _class, String msg) {
        if (isDebug)
            Logger.i(_class.getName(), msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Logger.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Logger.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Logger.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Logger.i(tag, msg);
    }

    //error
    public static void e(String msg, Throwable t) {
        if (isDebug)
            Logger.e(msg, t);
    }
}
