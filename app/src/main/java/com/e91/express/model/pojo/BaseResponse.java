package com.e91.express.model.pojo;

import java.io.Serializable;

/**
 * @author devin
 * @Class BaseResponse
 * @Date 16/5/1
 */
public class BaseResponse<T> implements Serializable {

    private String success;

    private T data;

    private String errorMessage;

    private int errorCode;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success='" + success + '\'' +
                ", data=" + data +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}