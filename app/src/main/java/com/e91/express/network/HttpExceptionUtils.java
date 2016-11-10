package com.e91.express.network;


public class HttpExceptionUtils {
    public static ResultException getResultException(Throwable throwable) {
        return ((ResultException) throwable);
    }
}
