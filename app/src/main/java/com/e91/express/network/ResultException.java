package com.e91.express.network;

import com.e91.express.model.pojo.BaseResponse;
import com.google.gson.Gson;

import java.io.IOException;

import retrofit.Response;

public class ResultException extends Throwable {


    private int errorCode;
    private String errorMessage;
    private transient Response<?> response;

    public ResultException(Response<?> response) {
        super("HTTP " + response.code() + " " + response.message() + "\n");
        try {
            Gson gson = new Gson();
            BaseResponse baseResponse = gson.fromJson(response.errorBody().string(), BaseResponse.class);
            this.errorCode = baseResponse.getErrorCode();
            this.errorMessage = baseResponse.getErrorMessage();
            this.response = response;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * The full HTTP response. This may be null if the exception was serialized.
     */
    public Response<?> response() {
        return response;
    }
}
