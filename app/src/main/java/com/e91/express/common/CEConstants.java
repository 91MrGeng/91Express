package com.e91.express.common;

/**
 * @author devin
 * @Class CEConstants
 * @Date 16/5/1
 */
public class CEConstants {

    public static final class Url{
        public static String BASE_URL = "http://123.207.244.86/wechatsise/index.php/express/";
    }

    public static final class Key{
        public static String LOGINED = "Logined";
    }

    public static final class SelectImage {
        public static final int REQUEST_IMAGE = 2;
    }

    public static final class Tools{
        public static final String TAG = "Express";
        public static final int CONNECT_TIMEOUT = 15 * 1000;//15s
        public static final int WRITE_TIMEOUT = 15 * 1000;//15s
        public static final int READ_TIMEOUT = 30 * 1000;//15s
    }
}
